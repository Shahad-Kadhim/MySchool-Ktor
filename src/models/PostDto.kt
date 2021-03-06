package com.example.models

import com.example.database.PostType


data class PostDto (
    val id: String,
    var title: String,
    var content: String,
    val payload: String?,
    val authorName: String,
    val datePosted: Long,
    val type: PostType,
)