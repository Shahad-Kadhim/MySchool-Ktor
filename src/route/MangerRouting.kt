package com.example.route

import com.example.authentication.JwtConfig
import com.example.models.BaseResponse
import com.example.repostiory.MangerRepository
import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.auth.jwt.*
import io.ktor.http.*
import io.ktor.response.*
import io.ktor.routing.*
import org.koin.java.KoinJavaComponent.inject

private val mangerRepository: MangerRepository by inject(MangerRepository::class.java)
fun Route.getAllManger(){
    get("/allManger"){
        call.respond(mangerRepository.getAllManger())
    }
}

fun Route.getMangerSchools(){
    get("/manger/schools") {
        call.principal<JWTPrincipal>()?.let {
            call.respond(
                BaseResponse(
                    HttpStatusCode.OK.value,
                    mangerRepository.getMangerSchools(
                        it.payload.getClaim(JwtConfig.CLAIM_ID).asString()
                    )
                )
            )
        }
    }
}

fun Route.getMangerClasses(){
    get("/manger/classes") {
        call.principal<JWTPrincipal>()?.let {
            call.respond(
                BaseResponse(
                    HttpStatusCode.OK.value,
                    mangerRepository.getMangerClasses(
                        call.request.queryParameters["id"] ?: it.payload.getClaim(JwtConfig.CLAIM_ID).asString()
                    )
                )
            )
        }
    }
}

fun Route.getMangerInfo(){
    get("manger/info") {
        call.principal<JWTPrincipal>()?.let {
            call.respond(
                BaseResponse(
                    HttpStatusCode.OK.value,
                    mangerRepository.getMangerInfo(
                        it.payload.getClaim(JwtConfig.CLAIM_ID).asString()
                    )
                )
            )
        }
    }
}