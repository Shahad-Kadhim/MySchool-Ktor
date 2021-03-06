package com.example.models

import com.example.database.PostType


data class Post (
    val id: String,
    val classId: String,
    var title: String,
    var content: String ,
    val payload: String?,
    val authorId: String ,
    val datePosted: Long ,
    val type: PostType,
)