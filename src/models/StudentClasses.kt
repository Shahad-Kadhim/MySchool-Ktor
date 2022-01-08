package com.example.models

import kotlinx.serialization.Serializable
import java.util.*

@Serializable
data class StudentClasses(
    val classId: String,
    val joinDate: Date
)