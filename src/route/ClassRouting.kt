package com.example.route

import com.example.authentication.JwtConfig
import com.example.models.BaseResponse
import com.example.models.Class
import com.example.requestBody.MembersEntityBody
import com.example.repostiory.ClassRepository
import com.example.repostiory.SchoolRepository
import com.example.requestBody.CreateClassBody
import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.auth.jwt.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import org.koin.java.KoinJavaComponent.inject
import java.util.*

private val classRepository: ClassRepository by inject(ClassRepository::class.java)
private val schoolRepository: SchoolRepository by inject(SchoolRepository::class.java)

fun Route.getAllClasses(){
    get("/allClass"){
        call.respond(classRepository.getAllClasses())
    }
}

fun Route.getClassById(){
    get("/class/{classId}") {
        call.parameters["classId"]?.let { classId ->
            classRepository.getClassById(classId)?.let { classInfo ->
                call.respond(
                    BaseResponse(
                        HttpStatusCode.Found.value,
                        classInfo
                    )
                )
            } ?: call.respond(HttpStatusCode.NotFound, "this Id : $classId not found")
        }
    }
}

fun Route.createClass() {
    post("class/new") {
        call.principal<JWTPrincipal>()?.let {
            val teacherId=it.payload.getClaim(JwtConfig.CLAIM_ID).asString()
            try {
                val newClass = call.receive<CreateClassBody>()
                schoolRepository.getSchoolTeacherByNameSchool(newClass.schoolName,teacherId)?.let { schoolId ->
                    Class(
                        id =  UUID.randomUUID().toString(),
                        name = newClass.name,
                        teacherId = teacherId,
                        schoolId = schoolId,
                        stage = newClass.stage
                    ).also { classInfo->
                        classRepository.addClass(classInfo)
                        call.respond(
                            BaseResponse(
                                HttpStatusCode.Created.value,
                                classInfo
                            )
                        )
                    }
                }

            }catch (e:Exception){
                call.respond(HttpStatusCode.BadRequest)
            }

        }
    }
}

fun Route.getClassMembers(){
    get("class/{classId}/members") {
        call.parameters["classId"]?.let {
            call.respond(
                BaseResponse(
                    HttpStatusCode.OK.value,
                    classRepository.getMembers(it)
                )
            )
        }
    }
}

fun Route.getStudentInSchool(){
    get("/studentSchool"){
        call.parameters["classId"]?.let{
            classRepository.getStudentInSchoolToAddToClass(it)?.let { students ->
                call.respond(
                    BaseResponse(
                        HttpStatusCode.OK.value,
                        students
                    )
                )
            }
        }
    }
}


fun Route.addStudentToCLass(){
    post("class/addMember"){
        try {
            call.receive<MembersEntityBody>().let {
                classRepository.addStudentToClass(it.membersId,it.entityId)
                call.respond(
                    BaseResponse(
                        HttpStatusCode.Accepted.value,
                        "STUDENTS JOIN SUCCESS"
                    )
                )
            }
        }catch (e: Exception){
            call.respond(HttpStatusCode.BadRequest)
        }
    }
}

fun Route.removeStudentFromClass(){
    post("class/removeMember"){
        try {
            call.receive<MembersEntityBody>().let {
                classRepository.removeStudent(it.membersId,it.entityId)
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