package com.chatapp.model

data class Message(
    val roomId: String? = null,
    val content: String? = null,
    val senderId: String? = null,
    val senderName: String? = null,
    val date:Long? = null
){
    companion object
    {
        val COLLECTION_NAME = "Messages"
    }
}
