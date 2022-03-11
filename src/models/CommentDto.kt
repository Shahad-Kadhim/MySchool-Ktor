package com.example.models


data class CommentDto(
    val id: String,
    val postId: String,
    val authorName: String,
    var content: String,
    val dateCommented: Long
)
