package com.example.models

import kotlinx.serialization.Serializable

@Serializable
data class Comment(
    val id: String ,
    val postId: String ,
    val authorId: String ,
    var content: String ,
    val dateCommented: String
)
