package com.example.models

import kotlinx.serialization.Serializable

@Serializable
data class StudentClasses(
    val classId: Long,
    val joinDate: String
)