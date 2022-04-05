package com.example.route

import com.transloadit.sdk.Assembly
import com.transloadit.sdk.Transloadit
import io.ktor.http.content.*
import java.io.File

class ImageUpload(
    private val transloadit: Transloadit
) {
    fun uploadImage(parts: List<PartData>, onUploadImage:(String?) -> Unit) {
        with(transloadit.newAssembly().addReSizeImage()){
            extractImage(parts){
                onUploadImage(
                    it?.let { file ->
                        addFile(file)
                        save().id
                    }
                )
            }
        }
    }

    private fun Assembly.addReSizeImage(): Assembly =
        this.apply {
            addStep(
                "resize",
                "/image/resize",
                hashMapOf<String,Any>(Pair("width",75), Pair("width",75))
            )
        }


    private fun extractImage(parts: List<PartData>, onReceiveFile: (File?)-> Unit) {
        with(parts.find { it.name == "image"} as? PartData.FileItem){
            this?.originalFileName.let { name ->
                when(name) {
                    is String -> {
                        onReceiveFile(
                            File.createTempFile(
                                name.substring(0,name.lastIndexOf(".")),
                                name.substring(name.lastIndexOf("."))
                            ).apply {
                                writeBytes(this@with!!.streamProvider().readBytes())
                            }
                        )
                    }
                    null -> onReceiveFile(name)
                }
            }
        }
    }
}