package com.example.route

import com.transloadit.sdk.Transloadit

class LoadImage (
    private val transloadit: Transloadit
) {
    fun getImageUrl(imageId: String) =
        transloadit.getAssembly(imageId).url

    fun getImageBitmap(imageId: String){

    }
}