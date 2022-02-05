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

fun Route.registerTeacher(){
    post("/teacher/new"){
        try {
            val newUser = call.receive<TeacherRegisterBody>()

            val teacher = Teacher(
                id =  UUID.randomUUID().toString(),
                name = newUser.name,
                password= newUser.password,
                teachingSpecialization = newUser.teachingSpecialization,
                phone = newUser.phone,
            )
            println("TEACHEREGISTER  ::::: ${teacher.id}  :::::: ")
            teacherRepository.addTeacher(teacher)
            call.respond(jwtConfig.generateToken(JwtConfig.JwtUser( teacher.id,Role.TEACHER)))

        }catch (e:Exception){
            println(e.message)
            call.respond(HttpStatusCode.BadRequest)
        }
    }
}

fun Route.loginTeacher(){
    post("/teacher/login") {
        val loginBody = call.receive<LoginBody>()

        val user = teacherRepository.getTeacherByNameAndPassword(loginBody.name, loginBody.password)

        if (user == null) {
            call.respond(HttpStatusCode.Unauthorized, "Invalid credentials!")
            return@post
        }

        val token = jwtConfig.generateToken(JwtConfig.JwtUser(user.id, Role.TEACHER))
        call.respond(token)
    }
}

fun Route.getTeacherClasses(){
    get("/teacher/classes") {

        call.principal<JWTPrincipal>()?.let {
            call.respond(
                BaseResponse(
                    HttpStatusCode.OK.value,
                    teacherRepository.getTeacherClasses(
                        it.payload.getClaim(JwtConfig.CLAIM_ID).asString()
                    ),
                )
            )
        }
    }
}