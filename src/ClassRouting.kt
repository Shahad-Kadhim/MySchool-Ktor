package com.example

import com.example.models.Class
import com.example.repostiory.Repository
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.response.*
import io.ktor.routing.*

//fun Route.getAllClass(){
//    get("{studentId}/classes"){
//        call.parameters["studentId"]?.toLongOrNull()?.let { studentId ->
//            Repository.getStudentClasses(studentId)
//        }?.let { classes ->
//            call.respond(classes)
//        }
//    }
//}

fun Route.getClassById(){
    get("/classes/{classId}") {
        call.parameters["classId"]?.toLongOrNull()?.let { classId ->
            Repository.getClassById(classId)?.let {
                call.respond(it)
            } ?: call.respond(HttpStatusCode.NotFound, "this Id : $classId not found")
        }
    }
}

fun Route.createClass() {
    get("/createClass") {
        call.request.also {
            it.queryParameters["className"]?.let { className ->
                it.queryParameters["schoolId"]?.toLongOrNull()?.let { schoolId ->
                    it.queryParameters["teacherId"]?.toLongOrNull()?.let { teacherId ->
                        it.queryParameters["stage"]?.toIntOrNull()?.let { stage ->
                            call.respond(
                                Repository.addClass(
                                    Class(
                                        0,
                                        className,
                                        teacherId,
                                        schoolId,
                                        stage
                                    )
                                )
                            )
                        }
                    }
                }
            }
        }
    }
}

fun Route.getClassMembers(){
    get("{classId}/members") {
        call.parameters["classId"]?.toLongOrNull()?.let {
            Repository.getMembers(it)
        }

    }
}