package com.chatapp.home

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.chatapp.base.BaseViewModel
import com.chatapp.model.FireBaseUtils
import com.chatapp.model.Room

class HomeViewModel : BaseViewModel() {
    val events = mutableStateOf<HomeViewEvents>(HomeViewEvents.Idle)
    val roomsList = mutableStateListOf<Room>()
    fun getRooms() {
        isLoading.value=true
        FireBaseUtils.getRoom(
            onSuccessListener = { querySnapshot ->
                isLoading.value=false
                querySnapshot.documents.forEach {
                    val room=it.toObject(Room::class.java)
                    roomsList.add(room!!)
                }


            }, onFailureListener = {
                isLoading.value=false
                Log.e("TAG", "error occurred: ${it.message}" )

            })
    }
fun navigateToRoomChat(room:Room){
events.value=HomeViewEvents.NavigateToRoomChat(room)
}
    fun resetToIdle() {
        events.value = HomeViewEvents.Idle
    }

    fun navigateToAddRoom() {
        events.value = HomeViewEvents.NavigateToAddRoom
    }

}