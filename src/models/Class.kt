package com.example.models

import kotlinx.serialization.Serializable

@Serializable
data class Class (
    val id: String,
    var name:String,
    var teacherId: String ,
    var schoolId: String ,
    val stage :Int?,
)

