package com.example.route

import com.example.authentication.JwtConfig
import com.example.authentication.Role
import com.example.authentication.hash
import com.example.dao.UserDao
import com.example.dao.toRole
import com.example.jwtConfig
import com.example.models.*
import com.example.requestBody.LoginBody
import com.example.requestBody.MangerRegisterBody
import com.example.requestBody.StudentRegisterBody
import com.example.requestBody.TeacherRegisterBody
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import java.util.*

val userDao =UserDao()

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
                            note = note
                        )
                    }

                    userDao.addUserStudent(student)
                    call.respond(BaseResponse(HttpStatusCode.Created.value, jwtConfig.generateToken(JwtConfig.JwtUser(student.id,Role.STUDENT))))

                }
                Role.TEACHER.name -> {
                    val teacher =call.receive<TeacherRegisterBody>().run {
                        Teacher(
                            id = UUID.randomUUID().toString(),
                            name = name,
                            password = hash(password),
                            phone = phone,
                            teachingSpecialization = teachingSpecialization
                        )
                    }

                    userDao.addUserTeacher(teacher)
                    call.respond(BaseResponse(HttpStatusCode.Created.value, jwtConfig.generateToken(JwtConfig.JwtUser(teacher.id,Role.TEACHER))))

                }
                Role.MANGER.name -> {
                    val manger = call.receive<MangerRegisterBody>().run {
                        Manger(
                            id = UUID.randomUUID().toString(),
                            name = name,
                            password = hash(password),
                            phone = phone,
                        )
                    }
                    println(manger.toString())
                    userDao.addUserManger(manger)
                    call.respond(
                        BaseResponse(
                            HttpStatusCode.Created.value,
                            TokenResponse(
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
            userDao.findUserByNameAndPassword(user.name, hash(user.password))?.let {
               call.respond(
                   BaseResponse(
                       HttpStatusCode.Found.value,
                       TokenResponse(
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