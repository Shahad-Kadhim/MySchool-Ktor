package com.example.models

import kotlinx.serialization.Serializable

@Serializable
data class Post (
    val id: String,
    val classId: String,
    var title: String,
    var content: String ,
    val authorId: String ,
    val datePosted: String ,
    var lastDateUpdated: String =datePosted,  // default take date of posted this post
)