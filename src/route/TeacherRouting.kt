package com.example.route

import com.example.authentication.JwtConfig
import com.example.authentication.Role
import com.example.jwtConfig
import com.example.models.BaseResponse
import com.example.models.Teacher
import com.example.repostiory.TeacherRepository
import com.example.requestBody.LoginBody
import com.example.requestBody.TeacherRegisterBody
import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.auth.jwt.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import org.koin.java.KoinJavaComponent.inject
import java.util.*

private val teacherRepository: TeacherRepository by inject(TeacherRepository::class.java)

fun Route.getAllTeacher(){
    get("/allTeacher"){
        call.respond(teacherRepository.getAllTeacher())
    }
}

fun Route.getTeacherClasses(){
    get("/teacher/classes") {
        call.principal<JWTPrincipal>()?.let {
            call.respond(
                BaseResponse(
                    HttpStatusCode.OK.value,
                    teacherRepository.getTeacherClasses(
                        it.payload.getClaim(JwtConfig.CLAIM_ID).asString(),
                        call.parameters["search"]
                    ),
                )
            )
        }
    }
}

fun Route.getTeacherSchools(){
    get("/teacher/schools") {
        call.principal<JWTPrincipal>()?.let {
            call.respond(
                BaseResponse(
                    HttpStatusCode.OK.value,
                    teacherRepository.getTeacherSchools(
                        it.payload.getClaim(JwtConfig.CLAIM_ID).asString()
                    ),
                )
            )
        }
    }
}
