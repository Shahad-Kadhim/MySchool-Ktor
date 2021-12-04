package com.example.models

import kotlinx.serialization.Serializable

@Serializable
data class MemberClass(
    val memberId: String,
    val ClassId: String,
    val joinDate: String
)