package com.chatapp.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Room(
    var id:String?=null,
    val roomName:String?=null,
    val roomDescription:String?=null,
    val categoryId:String? = null,
): Parcelable
