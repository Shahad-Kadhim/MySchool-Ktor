package com.example.com.example.models

data class DutyDto(
    val id: String,
    var title: String,
    var content: String,
    val payload: String?,
    val authorName: String,
    val datePosted: Long,
    val degree: Int?,
)