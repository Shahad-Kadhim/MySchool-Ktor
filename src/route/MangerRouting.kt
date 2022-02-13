package com.example.route

import com.example.authentication.JwtConfig
import com.example.authentication.Role
import com.example.jwtConfig
import com.example.models.BaseResponse
import com.example.models.Manger
import com.example.repostiory.MangerRepository
import com.example.requestBody.LoginBody
import com.example.requestBody.MangerRegisterBody
import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.auth.jwt.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import org.koin.java.KoinJavaComponent.inject
import java.util.*

private val mangerRepository: MangerRepository by inject(MangerRepository::class.java)
fun Route.getAllManger(){
    get("/allManger"){
        call.respond(mangerRepository.getAllManger())
    }
}

fun Route.registerManger(){
    post("/manger/new"){
        try {
            val newUser = call.receive<MangerRegisterBody>()

            val manger = Manger(
                id =  UUID.randomUUID().toString(),
                name = newUser.name,
                password= newUser.password
            )
            mangerRepository.addManger(manger)
            call.respond(jwtConfig.generateToken(JwtConfig.JwtUser( manger.id,Role.MANGER)))

        }catch (e:Exception){
            println(e.message)
            call.respond(HttpStatusCode.BadRequest)
        }
    }
}

fun Route.loginManger(){
    post("/manger/login") {
        val loginBody = call.receive<LoginBody>()

        val user = mangerRepository.getMangerByNameAndPassword(loginBody.name, loginBody.password)

        if (user == null) {
            call.respond(HttpStatusCode.Unauthorized, "Invalid credentials!")
            return@post
        }

        val token = jwtConfig.generateToken(JwtConfig.JwtUser( user.id,Role.MANGER))
        call.respond(token)
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