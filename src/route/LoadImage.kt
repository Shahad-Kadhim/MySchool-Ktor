package com.example.route

import com.example.route.transloaditResponse.TransLoadResponse
import com.google.gson.Gson
import com.transloadit.sdk.Transloadit
import org.json.JSONObject

class LoadImage (
    private val transloadit: Transloadit,
    private val gson: Gson
) {
    fun getImageUrl(imageId: String) =
        gson
            .fromJson(
                transloadit.getAssembly(imageId).json().toString(),
                TransLoadResponse::class.java
            )
            .results
            ?.resize
            ?.firstOrNull()
            ?.sslUrl ?: ""

}