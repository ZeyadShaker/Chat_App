package com.chatapp.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
data class AppUser(
    val userId: String?=null,
    val firstName: String?=null,
    val email: String?=null
): Parcelable
