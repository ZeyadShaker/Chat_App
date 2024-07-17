package com.chatapp.register

import com.chatapp.model.AppUser

sealed interface RegisterViewEvent {
    data object Idle: RegisterViewEvent
    data class NavigateToHome(val user:AppUser): RegisterViewEvent
}