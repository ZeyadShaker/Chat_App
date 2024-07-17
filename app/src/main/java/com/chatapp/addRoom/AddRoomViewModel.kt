package com.chatapp.addRoom

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.chatapp.base.BaseViewModel
import com.chatapp.model.Category
import com.chatapp.model.FireBaseUtils
import com.chatapp.model.Room

class AddRoomViewModel : BaseViewModel() {
    val roomNameState = mutableStateOf("")
    val roomNameErrorState = mutableStateOf<String?>(null)
    val roomDescriptionState = mutableStateOf("")
    val roomDescriptionErrorState = mutableStateOf<String?>(null)
    val isExpandedState = mutableStateOf(false)
    val categoriesList = Category.getCategoriesList()
    val selectedCategoryItem = mutableStateOf(categoriesList.get(0))
    val isDone = mutableStateOf(false)
    val events= mutableStateOf<AddRoomViewEvents>(AddRoomViewEvents.Idle)

    fun addRoom() {
        if (validateFields()) {
            isLoading.value=true
            //add room
            val room =
                Room(categoryId = selectedCategoryItem.value.id, roomName = roomNameState.value, roomDescription = roomDescriptionState.value)
            FireBaseUtils.addRoom(room, onSuccessListener = {
                isLoading.value=false
                isDone.value = true
                navigateBack()


            }, onFailureListener = {
                isLoading.value=false
                Log.e("TAG", "addRoom: ${it.message}", )

            })
        }


    }
    fun navigateBack(){
        events.value=AddRoomViewEvents.NavigateBack
    }

    fun validateFields(): Boolean {
        if (roomNameState.value.isEmpty() || roomNameState.value.isEmpty()) {
            roomNameErrorState.value = "required"
            return false
        } else {
            roomNameErrorState.value = null
        }
        if (roomDescriptionState.value.isEmpty() || roomDescriptionState.value.isEmpty()) {
            roomDescriptionErrorState.value = "required"
            return false
        } else {
            roomDescriptionErrorState.value = null
        }
        return true
    }
}