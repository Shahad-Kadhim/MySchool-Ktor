package com.example.route

import com.example.authentication.JwtConfig
import com.example.authentication.Role
import com.example.authentication.hash
import com.example.dao.toRole
import com.example.jwtConfig
import com.example.models.*
import com.example.repostiory.UserRepository
import com.example.requestBody.LoginBody
import com.example.requestBody.MangerRegisterBody
import com.example.requestBody.StudentRegisterBody
import com.example.requestBody.TeacherRegisterBody
import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.auth.jwt.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import org.koin.java.KoinJavaComponent
import java.util.*

private val userRepository: UserRepository by KoinJavaComponent.inject(UserRepository::class.java)

fun Route.addUser(){
    post("/user/create") {
        val role = call.request.queryParameters["role"]
        try{
            when (role) {
                Role.STUDENT.name -> {
                    val student =call.receive<StudentRegisterBody>().run {
                        Student(
                            id = UUID.randomUUID().toString(),
                            name = name,
                            password = hash(password),
                            phone = phone,
                            age = age,
                            stage = stage,
                            note = note,
                            firebaseToken = firebaseToken
                        )
                    }

                    userRepository.addStudentUser(student)
                    call.respond(
                        BaseResponse(
                            HttpStatusCode.Created.value,
                            AuthenticationResponse(
                                role.toRole(),
                                jwtConfig.generateToken(JwtConfig.JwtUser(student.id,Role.STUDENT))
                            )
                        )
                    )

                }
                Role.TEACHER.name -> {
                    val teacher =call.receive<TeacherRegisterBody>().run {
                        Teacher(
                            id = UUID.randomUUID().toString(),
                            name = name,
                            password = hash(password),
                            phone = phone,
                            teachingSpecialization = teachingSpecialization,
                            firebaseToken = firebaseToken
                        )
                    }

                    userRepository.addTeacherUser(teacher)
                    call.respond(
                        BaseResponse(
                            HttpStatusCode.Created.value,
                            AuthenticationResponse(
                                role.toRole(),
                                jwtConfig.generateToken(JwtConfig.JwtUser(teacher.id,Role.TEACHER))
                            )
                        )
                    )

                }
                Role.MANGER.name -> {
                    val manger = call.receive<MangerRegisterBody>().run {
                        Manger(
                            id = UUID.randomUUID().toString(),
                            name = name,
                            password = hash(password),
                            phone = phone,
                            firebaseToken =firebaseToken
                        )
                    }
                    println(manger.toString())
                    userRepository.addMangerUser(manger)
                    call.respond(
                        BaseResponse(
                            HttpStatusCode.Created.value,
                            AuthenticationResponse(
                                role.toRole(),
                                jwtConfig.generateToken(JwtConfig.JwtUser(manger.id,Role.MANGER)
                                )
                            )
                        )
                    )
                }
                else -> call.respond(HttpStatusCode.BadRequest)
            }
        }catch (e: Exception){
            call.respond(HttpStatusCode.BadRequest)
        }
    }
}


fun Route.loginUser(){
    post("/user/login") {
        try {
            val user = call.receive<LoginBody>()
            userRepository.loginUser(user.name, hash(user.password),user.firebaseToken)?.let {
               call.respond(
                   BaseResponse(
                       HttpStatusCode.Found.value,
                       AuthenticationResponse(
                           it.role,
                           jwtConfig.generateToken(JwtConfig.JwtUser(it.id,it.role))
                       )
                   )
               )
            }
        }catch (e: Exception){
            call.respond(HttpStatusCode.BadRequest)
        }
    }
}

fun Route.getInfo(){
    get("/getInfo"){
        call.principal<JWTPrincipal>()?.let {
            call.respond(
                BaseResponse(
                    HttpStatusCode.OK.value,
                    userRepository.getUserInfo(
                        it.payload.getClaim(JwtConfig.CLAIM_ID).asString()
                    )
                )
            )
        }
    }
}

fun Route.addRole(){
    post("/role/add") {
        call.request.queryParameters["role"]?.let { role ->
            try{
                userRepository.addRole(role)
                call.respond(
                    BaseResponse(
                        HttpStatusCode.Created.value,
                        role
                    )
                )
            }catch (e:Exception){
                call.respond(HttpStatusCode.BadRequest)
            }
        }
    }
}

fun Route.getRoles(){
    get("/roles") {
        call.respond(
            BaseResponse(
                HttpStatusCode.OK.value,
                userRepository.getRoles()
            )
        )
    }
}