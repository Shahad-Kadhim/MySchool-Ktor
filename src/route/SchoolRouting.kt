package com.example.route

import com.example.authentication.JwtConfig
import com.example.database.entities.TeachersSchool
import com.example.models.BaseResponse
import com.example.models.School
import com.example.models.SchoolDto
import com.example.models.TeacherSchool
import com.example.repostiory.SchoolRepository
import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.auth.jwt.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction
import org.koin.java.KoinJavaComponent.inject
import java.util.*

private val schoolRepository: SchoolRepository by inject(SchoolRepository::class.java)

fun Route.getAllSchool(){
    get("/allSchool"){
        call.respond(schoolRepository.getAllSchool())
    }
}

fun Route.getSchoolById(){
    get("/school/{schoolId}") {
        call.parameters["schoolId"]?.let { schoolId ->
            schoolRepository.getSchoolById(schoolId)?.let { school->
                call.respond(
                    BaseResponse(
                        HttpStatusCode.Found.value,
                        school
                    )
                )
            } ?: call.respond(HttpStatusCode.NotFound, "this Id : $schoolId not found")
        }
    }
}

fun Route.createSchool() {
    post("school/new") {
        val schoolName = call.request.queryParameters["name"]
        call.principal<JWTPrincipal>()?.let { jwtUser ->
            schoolName?.let { schoolName ->
                val school = School(
                    id = UUID.randomUUID().toString(),
                    name = schoolName,
                    mangerId = jwtUser.payload.getClaim(JwtConfig.CLAIM_ID).asString()
                )
                try {
                    schoolRepository.addSchool(school)
                    call.respond(
                        BaseResponse(
                            HttpStatusCode.Created.value,
                            school
                        )
                    )
                } catch (e: Exception) {
                    call.respond(HttpStatusCode.BadRequest)
                }
            }
        }
    }
}

fun Route.getSchoolClasses(){
    get("School/{schoolId}/classes") {
        call.parameters["schoolId"]?.let {
            call.respond(
                BaseResponse(
                    HttpStatusCode.OK.value,
                    schoolRepository.getClasses(it)
                )
            )
        }
    }
}

fun Route.joinToSchool() {
    post("/teacher/joinSchool") {
        val schoolName = call.request.queryParameters["school_name"]
        call.principal<JWTPrincipal>()?.let { jwt ->
            schoolName?.let {
                schoolRepository.getSchoolByName(it)?.let { schoolId ->
                    try {
                        schoolRepository.addTeacher(
                            schoolId,
                            jwt.payload.getClaim(JwtConfig.CLAIM_ID).asString().toString()
                        )
                        call.respond(
                            BaseResponse(
                                HttpStatusCode.OK.value,
                                "TEACHER JOIN SUCCESS")
                        )
                    }catch (e:Exception){
                        call.respond(HttpStatusCode.BadRequest)
                    }
                }
            }
        }
    }
}

fun Route.getTeachers(){
    get("/school/teachers") {
        val schoolName = call.request.queryParameters["school_name"]
            schoolName?.let {
                schoolRepository.getSchoolByName(it)?.let { schoolId ->
                    call.respond(
                        BaseResponse(
                            HttpStatusCode.OK.value,
                            schoolRepository.getTeachers(schoolId)
                        )
                    )
                }
        }
    }
}

fun Route.joinStudentToSchool() {
    post("/student/joinSchool") {
        println("GGGGGGGGGGGGGGGGGGGGGGGGG")
        val schoolName = call.request.queryParameters["school_name"]
        call.principal<JWTPrincipal>()?.let { jwt ->
            println("GGGGGGGGGGGGGGGGGGGGGGGGG")
            schoolName?.let {
                schoolRepository.getSchoolByName(it)?.let { schoolId ->
                    try {
                        println("GGGGGGGGGGGGGGGGGGGGGGGGG")
                        schoolRepository.addStudent(
                            schoolId,
                            jwt.payload.getClaim(JwtConfig.CLAIM_ID).asString().toString()
                        )
                        call.respond(
                            BaseResponse(
                                HttpStatusCode.OK.value,
                                "STUDENT JOIN SUCCESS")
                        )
                    }catch (e:Exception){
                        call.respond(HttpStatusCode.BadRequest)
                    }
                }
            }
        }
    }
}

fun Route.getStudent(){
    get("/school/students") {
        val schoolName = call.request.queryParameters["school_name"]
            schoolName?.let {
                schoolRepository.getSchoolByName(it)?.let { schoolId ->
                    call.respond(
                        BaseResponse(
                            HttpStatusCode.OK.value,
                            schoolRepository.getStudents(schoolId)
                        )
                    )
                }
        }
    }
}


//for test
fun Route.getAllTeacherSchool(){
    get("/allTeacherSchool"){
        call.respond(
            transaction {
                TeachersSchool.selectAll().map {
                    TeacherSchool(
                        it[TeachersSchool.teacherId],
                        it[TeachersSchool.schoolId],
                        it[TeachersSchool.dateJoined]
                    )
                }

            }
        )
    }
}