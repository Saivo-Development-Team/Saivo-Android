package com.saivo.recommendo.util.helpers

import android.content.Context
import android.util.Base64
import android.widget.Toast
import androidx.room.TypeConverter
import at.favre.lib.bytes.BinaryToTextEncoding
import at.favre.lib.crypto.bcrypt.BCrypt
import kotlinx.coroutines.*
import org.threeten.bp.OffsetDateTime
import org.threeten.bp.format.DateTimeFormatter
import java.util.*

fun <T> lazyLoad(func: suspend CoroutineScope.() -> T): Lazy<Deferred<T>> {
    return lazy {
        GlobalScope.async(start = CoroutineStart.LAZY) {
            func.invoke(this)
        }
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
    println(string)
   return string.toByteArray()
}

fun createUUID(): String {
    return UUID.randomUUID().toString().trim()
}
