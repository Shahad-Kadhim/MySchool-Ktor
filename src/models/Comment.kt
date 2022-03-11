package com.example.models


data class Comment(
    val id: String,
    val postId: String,
    val authorId: String,
    var content: String,
    val dateCommented: Long
)
