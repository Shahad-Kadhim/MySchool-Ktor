package com.example.models

import com.example.authentication.Role


data class UserDto(
    val id: String,
    val name: String,
    val phone: Long,
    val role: Role
)
