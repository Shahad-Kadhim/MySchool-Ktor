package com.example.route.transloaditResponse


import com.example.route.transloaditResponse.Resize
import com.google.gson.annotations.SerializedName

data class Results(
    @SerializedName("resize")
    val resize: List<Resize>? = null
)