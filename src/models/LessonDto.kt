package com.example.models

import com.example.database.PostType


data class LessonDto (
    val id: String,
    var title: String,
    var content: String,
    val payload: String?,
    val authorName: String,
    val datePosted: Long,
    val lastUpdate: Long?,
)