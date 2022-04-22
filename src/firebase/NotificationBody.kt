package com.example.firebase

import com.google.gson.annotations.SerializedName

data class NotificationBody(
    val data: Data,
    @SerializedName("registration_ids") val UsersToken: List<String>
)
