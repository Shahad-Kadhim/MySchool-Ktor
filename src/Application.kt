package com.example

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.example.authentication.JwtConfig
import com.example.authentication.Role
import com.example.database.DatabaseManager
import com.example.database.entities.*
import com.example.di.appModule
import com.example.route.*
import com.example.util.toRole
import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.auth.jwt.*
import io.ktor.features.*
import io.ktor.gson.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction
import org.koin.ktor.ext.Koin

fun main(args: Array<String>) {
    embeddedServer(
        Netty,
        System.getenv("PORT").toInt() ?: 7777,
        module = Application::module
    ).start()
}


val secret =System.getenv("KTOR_JWT_SECRET")?: "default_value"
val jwtConfig = JwtConfig(secret)

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
        jwt {  }
        jwtConfig.teacherAuth(this)
        jwtConfig.mangerAuth(this)
        jwtConfig.studentAuth(this)
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
        manger()
        school()
        teacher()
        student()
        classes()
        testRoute()
    }
}

fun Routing.testRoute() {
    getAllClasses()
    getAllStudent()
    getAllTeacher()
    getAllSchool()
    getAllManger()
}

fun Routing.student() {
    registerStudent()
    loginStudent()
}

fun Routing.teacher() {
    registerTeacher()
    loginTeacher()
    authenticate("auth-teacher") {
        getTeacherClasses()
    }
}

fun Routing.school() {
    authenticate("auth-manger") {
        getSchoolById()
        createSchool()
        getSchoolClasses()
    }
}

fun Routing.classes() {
    authenticate("auth-teacher") {
        createClass()
        getClassById()
        getClassMembers()
    }
}

fun Routing.manger() {
    registerManger()
    loginManger()
    authenticate("auth-manger") {
        getMangerSchools()
    }
}

