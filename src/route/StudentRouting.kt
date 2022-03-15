package com.example.route

import com.example.authentication.JwtConfig
import com.example.authentication.Role
import com.example.jwtConfig
import com.example.models.BaseResponse
import com.example.models.Student
import com.example.repostiory.StudentRepository
import com.example.requestBody.LoginBody
import com.example.requestBody.StudentRegisterBody
import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.auth.jwt.*
import io.ktor.http.*
import io.ktor.http.cio.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import org.koin.java.KoinJavaComponent.inject
import java.util.*

private val studentRepository: StudentRepository by inject(StudentRepository::class.java)

fun Route.getAllStudent(){
    get("/allStudent"){
        call.respond(studentRepository.getAllStudent())
    }
}

fun Route.getAllClass(){
    get("/student/classes"){
        call.principal<JWTPrincipal>()?.let {
            call.respond(
                BaseResponse(
                    HttpStatusCode.OK.value,
                    studentRepository.getStudentClasses(
                        it.payload.getClaim(JwtConfig.CLAIM_ID).asString()
                    )
                )
            )
        }
    }
}

fun Route.getStudentInfo(){
    get("student/info"){
        call.principal<JWTPrincipal>()?.let{
            call.respond(
                BaseResponse(
                    HttpStatusCode.OK.value,
                    studentRepository.getStudentInfo(
                        call.request.queryParameters["id"] ?: it.payload.getClaim(JwtConfig.CLAIM_ID).asString()
                    )
                )
            )
        }
    }
}
