package com.example.route

import com.auth0.jwt.JWT
import com.example.authentication.JwtConfig
import com.example.models.BaseResponse
import com.example.models.School
import com.example.repostiory.SchoolRepository
import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.auth.jwt.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
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
        try {

            val schoolName= call.request.queryParameters["name"]
            call.principal<JwtConfig.JwtUser>()?.id?.apply {
                println("this is id  : $this")
            }
            call.principal<JWTPrincipal>()?.let { jwtUser->
//                println(jwtUser.id+jwtUser.name+jwtUser.password)
                schoolName?.let { schoolName ->
                    val school = School(
                        id = UUID.randomUUID().toString(),
                        name = schoolName,
                        mangerId = jwtUser.payload.getClaim(JwtConfig.CLAIM_ID).asString().apply {
                            println(this)
                        }
                    )
                    schoolRepository.addSchool(school)
                    call.respond(HttpStatusCode.Created)
                }
            }
        }catch (e:Exception){
            call.respond(HttpStatusCode.BadRequest)
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


