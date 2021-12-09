package com.example

import com.example.authentication.JwtConfig
import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.auth.jwt.*
import io.ktor.features.*
import io.ktor.gson.*
import io.ktor.response.*
import io.ktor.routing.*

fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)


val jwtConfig = JwtConfig(System.getenv("KTOR_JWT_SECRET"))

@Suppress("unused") // Referenced in application.conf
@kotlin.jvm.JvmOverloads
fun Application.module(testing: Boolean = false) {
    install(CallLogging)
    install(Authentication){
        jwt {
            jwtConfig.configureKtorFeature(this)
        }
    }

    install(ContentNegotiation){
        gson{
            setPrettyPrinting()
        }
    }
    routing {
        registerStudent()
        loginStudent()
        getAllStudent()
    }
}

