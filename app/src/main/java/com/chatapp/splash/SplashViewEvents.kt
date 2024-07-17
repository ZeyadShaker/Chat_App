package com.chatapp.splash

import com.chatapp.model.AppUser

sealed interface SplashViewEvents {
    data object Idle : SplashViewEvents
    data object NavigateToLogin : SplashViewEvents
    data class NavigateToHome(val user: AppUser) : SplashViewEvents

}