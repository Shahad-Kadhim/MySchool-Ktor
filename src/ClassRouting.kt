package com.example

import com.example.models.Class
import com.example.repostiory.Repository
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.response.*
import io.ktor.routing.*
import java.util.*

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
//    get("/classes/{classId}") {
//        call.parameters["classId"]?.let { classId ->
//            Repository.getClassById(classId)?.let {
//                call.respond(it)
//            } ?: call.respond(HttpStatusCode.NotFound, "this Id : $classId not found")
//        }
//    }
}

fun Route.createClass() {
//    get("class/new") {
//        call.request.also {
//            it.queryParameters["className"]?.let { className ->
//                it.queryParameters["schoolId"]?.let { schoolId ->
//                    it.queryParameters["teacherId"]?.let { teacherId ->
//                        it.queryParameters["stage"]?.toIntOrNull()?.let { stage ->
//                            call.respond(
//                                Repository.addClass(
//                                    Class(
//                                        UUID.randomUUID().toString(),
//                                        className,
//                                        teacherId,
//                                        schoolId,
//                                        stage
//                                    )
//                                )
//                            )
//                        }
//                    }
//                }
//            }
//        }
//    }
}

fun Route.getClassMembers(){
//    get("class/{classId}/members") {
//        call.parameters["classId"]?.let {
//            Repository.getMembers(it)
//        }
//    }
}