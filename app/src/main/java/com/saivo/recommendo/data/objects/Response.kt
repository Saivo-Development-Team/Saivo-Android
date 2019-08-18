package com.saivo.recommendo.data.objects

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.internal.LinkedTreeMap

data class Response (
    val data: Any? = null,
    val error: String = "",
    val status: String = "",
    val message: String = ""
) {
    fun <T: Any> getObjectFromData(type: Class<T>): T {
        val json: LinkedTreeMap<*, *> = data as LinkedTreeMap<*, *>
        val gson = GsonBuilder().registerTypeAdapter(type, Gson().getAdapter(type)).create()
        return gson.fromJson(gson.toJsonTree(json).asJsonObject, type)
    }
}