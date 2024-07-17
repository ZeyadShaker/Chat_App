package com.chatapp.register

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.chatapp.base.BaseViewModel
import com.chatapp.model.AppUser
import com.chatapp.model.DataUtils
import com.chatapp.model.FireBaseUtils
import com.google.firebase.Firebase
import com.google.firebase.auth.auth

class RegisterViewModel: BaseViewModel() {
    val firstNameState=mutableStateOf("")
    val firstNameErrorState=mutableStateOf<String?>(null)
    val emailState=mutableStateOf("")
    val emailErrorState=mutableStateOf<String?>(null)
    val passwordState=mutableStateOf("")
    val passwordErrorState=mutableStateOf<String?>(null)
    val auth=Firebase.auth
    val events= mutableStateOf<RegisterViewEvent>(RegisterViewEvent.Idle)
    val errorMessages= mutableStateOf<String?>(null)
    fun validateFields():Boolean{
        if (firstNameState.value.isEmpty()||firstNameState.value.isBlank()){
            firstNameErrorState.value="required"
            return false
        }else
            firstNameErrorState.value=null
        if (emailState.value.isEmpty()||emailState.value.isBlank()){
            emailErrorState.value="required"
            return false
        }else
            emailErrorState.value=null
        if (passwordState.value.isEmpty()||passwordState.value.isBlank()){
            passwordErrorState.value="required"
            return false
        }else
            passwordErrorState.value=null

    return true
    }
    fun resetToIdle(){
        events.value=RegisterViewEvent.Idle

    }
    fun navigateToHome(user:AppUser){
        events.value=RegisterViewEvent.NavigateToHome(user)
    }
    fun authenticateUser(){
        if(!validateFields())return
        isLoading.value=true
        auth.createUserWithEmailAndPassword(emailState.value,passwordState.value)
            .addOnCompleteListener { task->
                isLoading.value=false
                if (!task.isSuccessful){
                    Log.e("tag","authenticateUser:${task.exception?.message}")
                    message.value=task.exception?.message
                    return@addOnCompleteListener
                }
                val userId=task.result?.user?.uid
                // add user to cloud firebase
                addUserToFireStore(userId!!)

            }


    }
    fun addUserToFireStore(userId:String){
        val user=AppUser(userId,firstNameState.value,emailState.value)
        FireBaseUtils.addUser(user,
            onFailureListener = {
                                message.value="${it.message}"

        }, onSuccessListener = {
            DataUtils.appUser=user
            navigateToHome(user)

    })
}
}