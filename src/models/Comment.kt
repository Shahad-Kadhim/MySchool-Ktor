package com.example.models

import kotlinx.serialization.Serializable

@Serializable
data class Comment(
    val id: Long ,
    val postId: Long ,
    val authorId: Long ,
    var content: String ,
    val dateCommented: String
)
