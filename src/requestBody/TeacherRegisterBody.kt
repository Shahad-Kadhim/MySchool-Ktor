package com.example.requestBody

data class TeacherRegisterBody (
    var name: String,
    var password: String,
    var teachingSpecialization: String,
    var phone: Long,
)