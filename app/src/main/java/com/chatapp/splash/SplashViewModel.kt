package com.chatapp.splash

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.chatapp.model.AppUser
import com.chatapp.model.DataUtils
import com.chatapp.model.FireBaseUtils
import com.google.firebase.Firebase
import com.google.firebase.auth.auth

class SplashViewModel: ViewModel() {
    val auth= Firebase.auth
    val events=mutableStateOf<SplashViewEvents>(SplashViewEvents.Idle)
    fun resetToIdle(){
        events.value=SplashViewEvents.Idle
    }
    fun navigateToLogin(){
        events.value=SplashViewEvents.NavigateToLogin
    }
    fun navigateToHome(user: AppUser){
        events.value=SplashViewEvents.NavigateToHome(user)
    }
fun getUserFromFireStore(){
    FireBaseUtils.getUser(userId = auth.currentUser?.uid!!,
        onSuccessListener ={
                           val user=it.toObject(AppUser::class.java)
            DataUtils.appUser=user
            if (user != null) {
                navigateToHome(user)
            }
        } ,
        onFailureListener = {navigateToLogin()})

}
    fun navigate() {
        if (auth.currentUser != null) {
            getUserFromFireStore()
        } else {
            navigateToLogin()
        }
    }
}