package com.example.requestBody

data class CreatePostBody(
    val title: String ,
    val content: String,
    val payload: String?,
    val classId: String,
    val type: String
)