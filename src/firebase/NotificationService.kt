package com.example.firebase

import NotificationResponse
import com.google.gson.JsonElement
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface NotificationService {
    @POST("/fcm/send")
    suspend fun sentNotification(@Body body: JsonElement): Response<NotificationResponse>
}