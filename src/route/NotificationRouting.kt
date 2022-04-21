package com.example.route

import com.example.authentication.JwtConfig
import com.example.models.BaseResponse
import com.example.repostiory.NotificationRepository
import com.example.repostiory.PostRepository
import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.auth.jwt.*
import io.ktor.http.*
import io.ktor.response.*
import io.ktor.routing.*
import org.koin.java.KoinJavaComponent

val notificationRepository: NotificationRepository by KoinJavaComponent.inject(NotificationRepository::class.java)

fun Route.getNotification(){
    get("/notification"){
        call.principal<JWTPrincipal>()?.let { jwt ->
            call.respond(
                BaseResponse(
                    HttpStatusCode.OK.value,
                    notificationRepository.getNotifications(jwt.payload.getClaim(JwtConfig.CLAIM_ID).asString())
                )
            )
        }
    }
}