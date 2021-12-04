package com.example.models

import kotlinx.serialization.Serializable

@Serializable
data class Class (
    var name:String,
    var teacherId :String ,
    var schoolId: String ,
    val stage :Int,
)

