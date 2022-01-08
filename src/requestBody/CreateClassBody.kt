package com.example.requestBody

data class CreateClassBody(
    var name:String,
    var teacherId: String ,
    var schoolId: String ,
    val stage :Int?,
)