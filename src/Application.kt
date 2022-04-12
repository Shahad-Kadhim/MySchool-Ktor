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


private val secret =System.getenv("KTOR_JWT_SECRET")?: "default_value"
val jwtConfig = JwtConfig(secret)

@Suppress("unused") // Referenced in application.conf
@kotlin.jvm.JvmOverloads
fun Application.module(testing: Boolean = false) {


    DatabaseManager().getDatabase()
    transaction {
        SchemaUtils.drop(
//            TeachersSchool,
//            MemberClass,
//            DutyStudent,
//            Duties,
//            Students,
//            Lesson,
//            Comments,
//            Posts,
//            Classes,
//            Teachers ,
//            Schools,
//            Mangers,
//            Users,
        )
        SchemaUtils.create(
            Roles,
            Users,
           Mangers,
            Schools,
            Teachers ,
            Posts,
            Comments,
            Students,
            Classes,
            Duties,
            Lesson,
            DutyStudent,
            MemberClass,
            TeachersSchool,
            StudentsSchool
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
        addUser()
        loginUser()
        getClassMembers()
        addRole()
        getRoles()
        testRoute()
        getLessonInCLass()
        getPostInCLass()
        getPostById()
        authenticate("auth-student","auth-manger","auth-teacher") {
            getCommentInPost()
            createComment()
            getTeacherInfo()
            getStudentInfo()
            getMangerInfo()
        }
        authenticate("auth-student","auth-manger") {
            getStudentClasses()
        }
        authenticate("auth-student","auth-teacher") {
            getSolution()
        }
    }
}

fun Routing.testRoute() {
    getAllClasses()
    getAllStudent()
    getAllTeacher()
    getAllSchool()
    getAllManger()
    getAllTeacherSchool()
}

fun Routing.student() {
    authenticate("auth-student") {
        getStudentSchool()
        addSolution()
    }
}

fun Routing.teacher() {
    authenticate("auth-teacher") {
        getTeacherClasses()
        getTeacherSchools()
        addStudentToCLass()
        removeStudentFromClass()
        getStudentInSchool()
        createPost()
        getSolutionsForDuty()
    }
}

fun Routing.school() {
    authenticate("auth-manger") {
        getSchoolById()
        createSchool()
        deleteSchool()
        joinTeacherToSchool()
        removeTeacherFromSchool()
        joinStudentToSchool()
        removeStudentFromSchool()
        getSchoolClasses()
    }
}

fun Routing.classes() {
    authenticate("auth-teacher") {
        createClass()
        getClassById()
    }
}

fun Routing.manger() {
    authenticate("auth-manger") {
        getMangerSchools()
        getTeachers()
        getStudent()
        getMangerClasses()
    }
}

