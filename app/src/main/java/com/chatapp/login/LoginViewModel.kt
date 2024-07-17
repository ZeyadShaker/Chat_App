package com.chatapp.login

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.chatapp.base.BaseViewModel
import com.chatapp.model.AppUser
import com.chatapp.model.DataUtils
import com.chatapp.model.FireBaseUtils
import com.google.firebase.Firebase
import com.google.firebase.auth.auth

class LoginViewModel : BaseViewModel() {
    val emailState = mutableStateOf("")
    val emailErrorState = mutableStateOf<String?>(null)
    val passwordState = mutableStateOf("")
    val passwordErrorState = mutableStateOf<String?>(null)
    val events = mutableStateOf<LoginViewEvent>(LoginViewEvent.Idle)
    val auth = Firebase.auth

    fun validateFields(): Boolean {
        if (emailState.value.isEmpty() || emailState.value.isBlank()) {
            emailErrorState.value = "required"
            return false
        } else {
            emailErrorState.value = null
        }
        if (passwordState.value.isEmpty() || passwordState.value.isBlank()) {
            passwordErrorState.value = "required"
            return false

        } else {
            passwordErrorState.value = null
        }
        if (passwordState.value.length < 6) {
            passwordErrorState.value = "Password must be at least 6 characters"
            return false
        }
        return true
    }
fun navigateToHome(user: AppUser){
    events.value = LoginViewEvent.NavigateToHome(user)

}
    fun resetToIdle() {
        events.value=LoginViewEvent.Idle
    }
    fun authenticateUser() {
        if (!validateFields()) return
        isLoading.value = true
        auth.signInWithEmailAndPassword(emailState.value, passwordState.value)
            .addOnCompleteListener { task ->
                isLoading.value = false
                if (!task.isSuccessful) {
                    message.value = task.exception?.message
                    return@addOnCompleteListener
                }
                FireBaseUtils.getUser(task.result?.user?.uid!!,
                    onSuccessListener = {
                        val user = it.toObject(AppUser::class.java)
                        DataUtils.appUser=user
                        navigateToHome(user!!)
                    },
                    onFailureListener = {
                        isLoading.value=false
                        message.value = "${it.message}"
                    })
            }


    }

    fun navigateToRegistration() {
        events.value = LoginViewEvent.NavigateToRegistration
    }

}