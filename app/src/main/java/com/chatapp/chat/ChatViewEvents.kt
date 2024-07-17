package com.chatapp.chat

sealed interface ChatViewEvents {
    data object Idle : ChatViewEvents
    data object NavigateBack : ChatViewEvents

}