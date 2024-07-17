package com.chatapp.addRoom

sealed interface AddRoomViewEvents {
    data object Idle : AddRoomViewEvents
    data object NavigateBack : AddRoomViewEvents
}