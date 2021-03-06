package com.example.route

import com.example.authentication.JwtConfig
import com.example.database.entities.TeachersSchool
import com.example.models.BaseResponse
import com.example.models.School
import com.example.models.TeacherSchool
import com.example.repostiory.SchoolRepository
import com.example.requestBody.AddUserBody
import com.example.requestBody.MembersEntityBody
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

fun Route.joinTeacherToSchool() {
    post("/teacher/joinSchool") {
        try {
            val body = call.receive<AddUserBody>()
            schoolRepository.addTeacher(body.schoolId, body.userName)?.let {
                call.respond(
                    BaseResponse(
                        HttpStatusCode.OK.value,
                        "TEACHER JOIN SUCCESS"
                    )
                )
            }
        } catch (e: Exception) {
            call.respond(HttpStatusCode.BadRequest)
        }
    }
}
fun Route.getTeachers(){
    get("/school/teachers") {
        val schoolId = call.request.queryParameters["school_id"]
        schoolId?.let {
            call.respond(
                BaseResponse(
                    HttpStatusCode.OK.value,
                    schoolRepository.getTeachers(
                        it,
                        call.parameters["search"]
                    )
                )
            )
        }
    }
}

fun Route.joinStudentToSchool() {
    post("/student/joinSchool") {
        try {
            val body = call.receive<AddUserBody>()
                schoolRepository.addStudent(body.schoolId, body.userName)?.let {
                    call.respond(
                        BaseResponse(
                            HttpStatusCode.OK.value,
                            "STUDENT JOIN SUCCESS"
                        )
                    )
                }
        }catch (e:Exception){
            call.respond(HttpStatusCode.BadRequest)
        }
    }

}

fun Route.getStudent(){
    get("/school/students") {
        val schoolId = call.request.queryParameters["school_id"]
        schoolId?.let {
            call.respond(
                BaseResponse(
                    HttpStatusCode.OK.value,
                    schoolRepository.getStudents(
                        it,
                        call.parameters["search"]
                    )
                )
            )
        }
    }
}

fun Route.deleteSchool(){
    post("school/delete") {
        val schoolId = call.request.queryParameters["id"]
        call.principal<JWTPrincipal>()?.let { jwtUser ->
            schoolId?.let { schoolId ->
                try {
                    schoolRepository.deleteSchool(schoolId)
                    call.respond(
                        BaseResponse(
                            HttpStatusCode.Created.value,
                            "REMOVED"
                        )
                    )
                } catch (e: Exception) {
                    call.respond(HttpStatusCode.BadRequest)
                }
            }
        }
    }
}


fun Route.removeStudentFromSchool(){
    post("school/removeStudent") {
        try {
            call.receive<MembersEntityBody>().let {
                schoolRepository.removeStudent(it.entityId,it.membersId)
                call.respond(
                    BaseResponse(
                        HttpStatusCode.Accepted.value,
                        "STUDENTS REMOVED SUCCESS"
                    )
                )
            }
        }catch (e: Exception){
            call.respond(HttpStatusCode.BadRequest)
        }
    }
}

fun Route.removeTeacherFromSchool(){
    post("school/removeTeacher") {
        try {
            call.receive<MembersEntityBody>().let {
                schoolRepository.removeTeacher(it.entityId,it.membersId)
                call.respond(
                    BaseResponse(
                        HttpStatusCode.Accepted.value,
                        "TEACHERS REMOVED SUCCESS"
                    )
                )
            }
        }catch (e: Exception){
            call.respond(HttpStatusCode.BadRequest)
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