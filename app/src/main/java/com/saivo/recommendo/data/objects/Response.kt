package com.saivo.recommendo.data.objects

import com.google.gson.Gson
import com.google.gson.internal.LinkedTreeMap

data class Response(
    val data: Any? = "",
    val error: String = "",
    val status: String = "",
    val message: String = ""
) {
    inline fun <reified T : Any> getObject(): T {
        Gson().let {
            return when (data) {
                is String -> data as T
                else -> it.fromJson(it.toJson((data as LinkedTreeMap<*, *>)), T::class.java)
            }
        }
    }

    @Suppress("UNCHECKED_CAST")
    inline fun <reified T : List<E>, reified E> getList(): T {
        Gson().apply {
            return (data as List<LinkedTreeMap<*, *>>).map {
                fromJson(toJson(it), E::class.java)
            } as T
        }
    }
}