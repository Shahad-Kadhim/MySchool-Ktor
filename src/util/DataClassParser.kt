package com.example.util

import com.google.gson.Gson
import com.google.gson.JsonElement
import com.google.gson.JsonParser

class  DataClassParser{

    fun parseToJson(order: Any): JsonElement {
        return JsonParser.parseString(Gson().toJson(order))
    }

}