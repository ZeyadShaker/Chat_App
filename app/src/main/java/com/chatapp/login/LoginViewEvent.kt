package com.chatapp.login

import com.chatapp.model.AppUser

sealed interface LoginViewEvent {
    data object Idle : LoginViewEvent
    data object NavigateToRegistration : LoginViewEvent
    data class NavigateToHome(val username: AppUser) : LoginViewEvent

}