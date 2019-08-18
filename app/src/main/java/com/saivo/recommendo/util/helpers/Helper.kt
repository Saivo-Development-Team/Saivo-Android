package com.saivo.recommendo.util.helpers

import kotlinx.coroutines.*

fun <T> lazyLoad(func: suspend CoroutineScope.() -> T): Lazy<Deferred<T>>{
    return lazy {
        GlobalScope.async(start = CoroutineStart.LAZY) {
            func.invoke(this)
        }
    }
}