package com.chatapp.chat

import android.util.Log
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.chatapp.model.DataUtils
import com.chatapp.model.FireBaseUtils
import com.chatapp.model.Message
import com.chatapp.model.Room
import com.google.firebase.firestore.DocumentChange
import java.util.Date

class ChatViewModel:ViewModel() {
    val events= mutableStateOf<ChatViewEvents>(ChatViewEvents.Idle)
    val messageState= mutableStateOf("")
    var room: Room?=null
    val messagesList= mutableStateListOf<Message>()



    fun navigateBack(){
        events.value=ChatViewEvents.NavigateBack
    }
    fun sendMessage(){
        if(messageState.value.isNotEmpty()||messageState.value.isNotBlank()){
            val message=Message(
                content = messageState.value,
                roomId =room?.id,
                date = Date().time,
                senderId = DataUtils.appUser?.userId,
                senderName = DataUtils.appUser?.firstName
            )
            FireBaseUtils.addMessage(message,
                onSuccessListener = {
                                    messageState.value= ""

                },
                onFailureListener = {
                    Log.e("TAG", "error occurred:${it.message} " )

            })
    }}
    fun getMessages(){

        FireBaseUtils.getMessagesUpdates(room?.id!!, eventListener = {querySnapshot, error ->

            if (error != null) {
                Log.e("TAG", "error occurred:${error.message} " )
                return@getMessagesUpdates
            }
            val list= mutableListOf<Message>()
            if (querySnapshot != null) {

                for (document in querySnapshot.documentChanges) {
                    when (document.type) {
                    DocumentChange.Type.ADDED ->{
                        list.add(document.document.toObject(Message::class.java))
                    }
                    DocumentChange.Type.MODIFIED->{}
                    DocumentChange.Type.REMOVED->{}
                }
                }
                list.addAll(messagesList)
                messagesList.clear()
                messagesList.addAll(list)

            }


        })
    }
}