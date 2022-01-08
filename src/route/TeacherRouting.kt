package com.example.route

import com.example.authentication.JwtConfig
import com.example.jwtConfig
import com.example.models.Teacher
import com.example.repostiory.TeacherRepository
import com.example.requestBody.LoginBody
import com.example.requestBody.TeacherRegisterBody
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import java.util.*

val repository= TeacherRepository()

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
            repository.addTeacher(teacher)
            call.respond(jwtConfig.generateToken(JwtConfig.JwtUser( teacher.name,teacher.password)))

        }catch (e:Exception){
            println(e.message)
            call.respond(HttpStatusCode.BadRequest)
        }
    }
}

fun Route.loginTeacher(){
    post("/teacher/login") {
        val loginBody = call.receive<LoginBody>()

        val user = repository.getTeacherByNameAndPassword(loginBody.name, loginBody.password)

        if (user == null) {
            call.respond(HttpStatusCode.Unauthorized, "Invalid credentials!")
            return@post
        }

        val token = jwtConfig.generateToken(JwtConfig.JwtUser(user.name, user.password))
        call.respond(token)
    }

}