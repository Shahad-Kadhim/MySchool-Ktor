package com.example

import com.example.authentication.JwtConfig
import com.example.database.DatabaseManager
import com.example.database.entities.*
import com.example.di.appModule
import com.example.route.*
import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.auth.jwt.*
import io.ktor.features.*
import io.ktor.gson.*
import io.ktor.routing.*
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction
import org.koin.ktor.ext.Koin

fun main(args: Array<String>) {
    io.ktor.server.netty.EngineMain.main(args)
}


val jwtConfig = JwtConfig(System.getenv("KTOR_JWT_SECRET")?: "default_value")

@Suppress("unused") // Referenced in application.conf
@kotlin.jvm.JvmOverloads
fun Application.module(testing: Boolean = false) {


    DatabaseManager().getDatabase()
    transaction {
        SchemaUtils.create(
           Mangers,
            Schools,
            Teachers ,
            Posts,
            Comments,
            Students,
            Classes,
            Duties,
            MemberClass,
            )
    }

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

    install(Koin) {
        modules(appModule)
    }

    routing {

        registerStudent()
        loginStudent()
        getAllStudent()
        registerTeacher()
        loginTeacher()
        createClass()
        getClassById()
        getClassMembers()
    }
}

