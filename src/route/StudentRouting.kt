package com.example.route

import com.example.authentication.JwtConfig
import com.example.jwtConfig
import com.example.models.Student
import com.example.repostiory.StudentRepository
import com.example.requestBody.LoginBody
import com.example.requestBody.StudentRegisterBody
import io.ktor.application.*
import io.ktor.http.*
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

fun Route.registerStudent(){
    post("/student/register"){
        try {
            val newUser = call.receive<StudentRegisterBody>()

            val student = Student(
                id =  UUID.randomUUID().toString(),
                name = newUser.name,
                password= newUser.password,
                age = newUser.age,
                note = newUser.note,
                phone = newUser.phone,
                stage = newUser.stage
            )
            studentRepository.addStudent(student)
            call.respond(jwtConfig.generateToken(JwtConfig.JwtUser( student.name,student.password)))

        }catch (e:Exception){
            println(e.message)
            call.respond(HttpStatusCode.BadRequest)
        }

    }
}

fun Route.loginStudent(){
    post("/student/login") {
        val loginBody = call.receive<LoginBody>()

        val user = studentRepository.getUserByNameAndPassword(loginBody.name, loginBody.password)

        if (user == null) {
            call.respond(HttpStatusCode.Unauthorized, "Invalid credentials!")
            return@post
        }

        val token = jwtConfig.generateToken(JwtConfig.JwtUser(user.name, user.password))
        call.respond(token)
    }
}


fun Route.getAllClass(){
    get("/student/{studentId}/classes"){
        call.parameters["studentId"]?.let { studentId ->
            studentRepository.getStudentClasses(studentId)
        }?.let { classes ->
            call.respond(classes)
        }
    }
}
