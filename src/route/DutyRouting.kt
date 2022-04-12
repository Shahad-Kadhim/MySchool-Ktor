package com.example.route

import com.example.authentication.JwtConfig
import com.example.models.BaseResponse
import com.example.models.DutySubmit
import com.example.models.Post
import com.example.repostiory.DutyRepository
import com.example.requestBody.CreatePostBody
import com.example.util.toPostType
import com.google.gson.Gson
import com.mysql.cj.log.Log
import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.auth.jwt.*
import io.ktor.http.*
import io.ktor.http.content.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import org.koin.java.KoinJavaComponent
import java.util.*

private val dutyRepository: DutyRepository by KoinJavaComponent.inject(DutyRepository::class.java)
private  val gson: Gson by KoinJavaComponent.inject(Gson::class.java)
private val imageUpload: ImageUpload by KoinJavaComponent.inject(ImageUpload::class.java)
private val loadImage: LoadImage by KoinJavaComponent.inject(LoadImage::class.java)

fun Route.addSolution(){
    post("duty/{dutyId}/addSolution") {
        call.principal<JWTPrincipal>()?.let { jwt ->
            try {
                call.receiveMultipart().readAllParts().let { parts ->
                    call.parameters["dutyId"]?.let {
                        imageUpload.uploadImage(parts) { imageId ->
                            dutyRepository.addSolution(
                                DutySubmit(
                                    studentId = jwt.payload.getClaim(JwtConfig.CLAIM_ID).asString(),
                                    dutyId = it,
                                    submitDate = Date().time,
                                    solutionLink = imageId!!
                                )
                            )
                        }
                        call.respond(
                            BaseResponse(
                                HttpStatusCode.OK.value,
                                "POST IS CREATED"
                            )
                        )
                    }
                }
            }catch (e: Exception){
                call.respond(HttpStatusCode.BadRequest)
            }
        }
    }
}

fun Route.getSolutionsForDuty(){
    get("duty/getSolutions") {
        call.principal<JWTPrincipal>()?.let { jwt ->
            try {
                call.request.queryParameters["dutyId"]?.let { dutyId ->
                    call.respond(
                        BaseResponse(
                            HttpStatusCode.OK.value,
                            dutyRepository.getSolutionsForDuty(dutyId)
                        )
                    )
                }
            } catch (e: Exception) {
                call.respond(HttpStatusCode.BadRequest)
            }
        }
    }
}

fun Route.getSolution(){
    get("duty/getSolution") {
        call.principal<JWTPrincipal>()?.let { jwt ->
            try {
                call.request.queryParameters["dutyId"]?.let { dutyId ->
                    dutyRepository.getSolution(
                        dutyId,
                        call.request.queryParameters["studentId"] ?: jwt.payload.getClaim(JwtConfig.CLAIM_ID).asString() ,
                    ).apply {
                        this?.let {
                            this.solutionLink = loadImage.getImageUrl(this.solutionLink)
                        }
                        call.respond(
                            BaseResponse(
                                HttpStatusCode.OK.value,
                                this
                            )
                        )

                    }
                }
            } catch (e: Exception) {
                print(e.message)
                call.respond("${e.message}")
            }
        }
    }
}
