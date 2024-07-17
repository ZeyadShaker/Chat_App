package com.chatapp.base

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

open class BaseViewModel:ViewModel() {
    val isLoading = mutableStateOf(false)
    val message= mutableStateOf<String?>("")
}