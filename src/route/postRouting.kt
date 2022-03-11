package com.example.route

import com.example.authentication.JwtConfig
import com.example.models.BaseResponse
import com.example.models.Post
import com.example.repostiory.ClassRepository
import com.example.repostiory.PostRepository
import com.example.requestBody.CreatePostBody
import com.example.util.toPostType
import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.auth.jwt.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import org.koin.java.KoinJavaComponent
import java.util.*

private val postRepository: PostRepository by KoinJavaComponent.inject(PostRepository::class.java)

fun Route.createPost(){
    post("post/create"){
        call.principal<JWTPrincipal>()?.let {
            try {
                with(call.receive<CreatePostBody>()){
                    postRepository.createPost(
                        Post(
                            id = UUID.randomUUID().toString(),
                            classId = this.classId,
                            title = this.title,
                            content = this.content,
                            payload = this.payload,
                            authorId = it.payload.getClaim(JwtConfig.CLAIM_ID).asString(),
                            datePosted = Date().time,
                            type = this.type.toPostType()
                        )
                    )
                    call.respond(
                        BaseResponse(
                            HttpStatusCode.OK.value,
                            "POST IS CREATED"
                        )
                    )
                }
            }catch (e: Exception){
                call.respond(HttpStatusCode.BadRequest)
            }
        }
    }
}



fun Route.getLessonInCLass(){
    get("post/getLesson"){
        try {
            call.parameters["classId"]?.let {
                call.respond(
                    BaseResponse(
                        HttpStatusCode.OK.value,
                        postRepository.getLessonByCLassId(it)
                    )
                )

            }
        }catch (e:Exception){
            call.respond(HttpStatusCode.BadRequest)
        }
    }
}

fun Route.getPostInCLass(){
    get("post/getPost"){
        try {
            call.parameters["classId"]?.let {
                call.respond(
                    BaseResponse(
                        HttpStatusCode.OK.value,
                        postRepository.getPostsByClassId(it)
                    )
                )

            }
        }catch (e:Exception){
            call.respond(HttpStatusCode.BadRequest)
        }
    }
}

//fun Route.upload(){
//    var fileDescription = ""
//    var fileName = ""
//
//    post("/upload") {
//        val multipartData = call.receiveMultipart()
//
//        multipartData.forEachPart { part ->
//            when (part) {
//                is PartData.FormItem -> {
//                    fileDescription = part.value
//                }
//                is PartData.FileItem -> {
//                    fileName = part.originalFileName as String
//                    var fileBytes = part.streamProvider().readBytes()
//                    File("uploads/$fileName").writeBytes(fileBytes)
//                }
//            }
//        }
//
//        call.respondText("$fileDescription is uploaded to 'uploads/$fileName'")
//    }
//}
//
//suspend fun ss(){
//    val response = HttpClient().submitFormWithBinaryData(
//        url = "http://localhost:8080/upload",
//        formData = formData {
//            append("description", "Ktor logo")
//            append("image", File("ktor_logo.png").readBytes(), Headers.build {
//                append(HttpHeaders.ContentType, "image/png")
//                append(HttpHeaders.ContentDisposition, "filename=ktor_logo.png")
//            })
//        }
//    ) {
//        onUpload { bytesSentTotal, contentLength ->
//            println("Sent $bytesSentTotal bytes from $contentLength")
//        }
//    }
//
//}
