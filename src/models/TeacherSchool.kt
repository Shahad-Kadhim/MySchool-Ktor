package com.example.models

import kotlinx.serialization.Serializable

@Serializable
data class TeacherSchool(
    val TeacherId: String,
    val SchoolId: String,
)