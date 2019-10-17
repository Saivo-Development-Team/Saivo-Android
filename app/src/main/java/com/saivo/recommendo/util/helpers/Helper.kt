package com.saivo.recommendo.util.helpers

import android.content.Context
import android.util.Base64
import android.util.Log
import android.widget.Toast
import com.saivo.recommendo.data.objects.Response
import kotlinx.coroutines.*
import java.util.*

fun <T> lazyLoad(func: suspend CoroutineScope.() -> T): Lazy<Deferred<T>> {
    return lazy {
        GlobalScope.async(start = CoroutineStart.LAZY) {
            func.invoke(this)
        }
    }
}

fun Throwable.logPrintStackTrace(caller: String? = "") {
    Log.e("$this from [$caller]", "[${this.cause}]: ${this.message}")
    this.printStackTrace()
}

fun Response.handleNullResponse(block: (Response.() -> Any)) {
    runCatching {
        block()
    }.onFailure {
        it.logPrintStackTrace("handleNullResponse / Your Network Call Failed")
    }
}

fun makeString(any: Any): String {
    return any.toString().trim()
}

fun toastMessage(context: Context?, message: String = "", duration: Int = 0) {
    Toast.makeText(context, message, duration).show()
}

fun base64encode(byteArray: ByteArray): String {
    return Base64.encodeToString(byteArray, Base64.NO_WRAP)
}

fun createByteArray(string: String): ByteArray {
   return string.toByteArray()
}

fun createUUID(): String {
    return UUID.randomUUID().toString().trim()
}
