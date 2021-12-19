package com.example.models

import kotlinx.serialization.Serializable

@Serializable
data class Post (
    val id: Long,
    val classId: Long,
    var title: String,
    var content: String ,
    val authorId: Long ,
    val datePosted: String ,
    var lastDateUpdated: String =datePosted,  // default take date of posted this post
)