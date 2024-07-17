package com.chatapp.model

import com.chatapp.Constants
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.Firebase
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.firestore

import com.google.firebase.firestore.EventListener
import com.google.firebase.firestore.Query

object FireBaseUtils {
    fun addUser(
        user: AppUser,
        onSuccessListener: OnSuccessListener<Void>,
        onFailureListener: OnFailureListener
    ) {
        Firebase.firestore.collection(Constants.USERS)
            .document(user.userId!!)
            .set(user)
            .addOnSuccessListener(onSuccessListener)
            .addOnFailureListener(onFailureListener)
    }

    fun getUser(
        userId: String,
        onSuccessListener: OnSuccessListener<DocumentSnapshot>,
        onFailureListener: OnFailureListener
    ) {
        Firebase.firestore
            .collection(Constants.USERS)
            .document(userId)
            .get()
            .addOnFailureListener(onFailureListener)
            .addOnSuccessListener(onSuccessListener)
    }

    fun addRoom(
        room: Room,
        onSuccessListener: OnSuccessListener<Void>,
        onFailureListener: OnFailureListener
    ) {
        val documentReference = Firebase.firestore.collection(Constants.ROOMS).document()
        room.id = documentReference.id

        documentReference.set(room)
            .addOnSuccessListener(onSuccessListener)
            .addOnFailureListener(onFailureListener)


    }

    fun getRoom(
        onSuccessListener: OnSuccessListener<QuerySnapshot>,
        onFailureListener: OnFailureListener
    ) {
        Firebase.firestore.collection(Constants.ROOMS).get().addOnSuccessListener(onSuccessListener)
            .addOnFailureListener(onFailureListener)
    }
    fun addMessage(message: Message, onSuccessListener: OnSuccessListener<Void>, onFailureListener: OnFailureListener){
        Firebase.firestore.
        collection(Constants.ROOMS).
        document(message.roomId!!).
        collection(Message.COLLECTION_NAME).
        document()
            .set(message)
            .addOnSuccessListener(onSuccessListener)
            .addOnFailureListener(onFailureListener)

    }
    fun getMessagesUpdates(roomId: String,eventListener:EventListener<QuerySnapshot> ){
        Firebase.firestore.collection(Constants.ROOMS)
            .document(roomId).collection(Message.COLLECTION_NAME)
            .orderBy("date",Query.Direction.DESCENDING)
            .addSnapshotListener(eventListener)
    }
}