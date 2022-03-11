package com.example.route

import com.example.authentication.JwtConfig
import com.example.models.BaseResponse
import com.example.models.Comment
import com.example.repostiory.CommentRepository
import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.auth.jwt.*
import io.ktor.http.*
import io.ktor.response.*
import io.ktor.routing.*
import org.koin.java.KoinJavaComponent
import java.util.*

private val commentRepository: CommentRepository by KoinJavaComponent.inject(CommentRepository::class.java)

fun Route.createComment(){
    post("comment/create"){
        call.principal<JWTPrincipal>()?.let {
            println(it.payload.getClaim(JwtConfig.CLAIM_ID).asString())
            call.request.queryParameters["postId"]?.let { postId ->
                commentRepository.createComment(
                    Comment(
                        id = UUID.randomUUID().toString(),
                        postId = postId,
                        authorId = it.payload.getClaim(JwtConfig.CLAIM_ID).asString(),
                        content = call.request.queryParameters["content"] ?: "",
                        dateCommented = Date().time
                    )
                )
                call.respond(
                    BaseResponse(
                        HttpStatusCode.OK.value,
                        "COMMENT IS CREATED"
                    )
                )
            }
        }
    }
}



fun Route.getCommentInPost(){
    get("{postId}/comment"){
        try {
            call.parameters["postId"]?.let {
                call.respond(
                    BaseResponse(
                        HttpStatusCode.OK.value,
                        commentRepository.getCommentsByPostId(it)
                    )
                )

            }
        }catch (e:Exception){
            call.respond(HttpStatusCode.BadRequest)
        }
    }
}
