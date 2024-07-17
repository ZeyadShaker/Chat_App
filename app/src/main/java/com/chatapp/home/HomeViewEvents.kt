package com.chatapp.home

import com.chatapp.model.Room

sealed interface HomeViewEvents {
     data object Idle : HomeViewEvents
     data object NavigateToAddRoom : HomeViewEvents
     data class NavigateToRoomChat(val room: Room) : HomeViewEvents
}