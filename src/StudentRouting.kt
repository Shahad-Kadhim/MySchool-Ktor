package com.example

import com.example.authentication.JwtConfig
import com.example.models.Student
import com.example.repostiory.Repository
import com.example.requestBody.LoginBody
import com.example.requestBody.RegisterBody
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import java.util.*

fun Route.getAllStudent(){
    get("/allStudent"){
        call.respond(Repository.getAllStudent())
    }
}

fun Route.registerStudent(){
    post("/register"){
        try {
            val newUser = call.receive<RegisterBody>()

            val student = Student(
                id =  UUID.randomUUID().toString().apply {
                                                         println("--------------------------------"+this)
                },
                name = newUser.name,
                password= newUser.password,
                age = newUser.age,
                note = newUser.note,
                phone = newUser.phone,
                stage = newUser.stage
            )

//            if(
                (Repository.addStudent(student))
//            ){
                call.respond(jwtConfig.generateToken(JwtConfig.JwtUser( student.name,student.password)))
//            }else{
//                call.respond(HttpStatusCode.Conflict,"Already account register")
//            }
        }catch (e:Exception){
            println(e.message)
            call.respond(HttpStatusCode.BadRequest)
        }

    }
}

fun Route.loginStudent(){
    post("/login") {
        val loginBody = call.receive<LoginBody>()

        val user = Repository.getUserByNameAndPassword(loginBody.name, loginBody.password)

        if (user == null) {
            call.respond(HttpStatusCode.Unauthorized, "Invalid credentials!")
            return@post
        }

        val token = jwtConfig.generateToken(JwtConfig.JwtUser(user.name, user.password))
        call.respond(token)
    }
}