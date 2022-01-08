package com.example.route

import com.example.authentication.JwtConfig
import com.example.jwtConfig
import com.example.models.Class
import com.example.models.Student
import com.example.repostiory.ClassRepository
import com.example.requestBody.CreateClassBody
import com.example.requestBody.StudentRegisterBody
import com.example.studentRepository
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import java.util.*

val classRepository = ClassRepository()

fun Route.getClassById(){
    get("/class/{classId}") {
        call.parameters["classId"]?.let { classId ->
            classRepository.getClassById(classId)?.let {
                call.respond(it)
            } ?: call.respond(HttpStatusCode.NotFound, "this Id : $classId not found")
        }
    }
}

fun Route.createClass() {
    post("class/new") {
        try {
            val newClass = call.receive<CreateClassBody>()

            val nClass = Class(
                id =  UUID.randomUUID().toString(),
                name = newClass.name,
                teacherId = newClass.teacherId,
                schoolId = newClass.schoolId,
                stage = newClass.stage
            )

            classRepository.addClass(nClass)
            call.respond(HttpStatusCode.Created)

        }catch (e:Exception){
            call.respond(HttpStatusCode.BadRequest)
        }

    }
}

fun Route.getClassMembers(){
    get("class/{classId}/members") {
        call.parameters["classId"]?.let {
            call.respond(classRepository.getMembers(it))
        }
    }
}


