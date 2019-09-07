package com.saivo.recommendo.util.exception

import java.io.IOException

class ConnectionOfflineException : IOException() {

    override val cause: Throwable?
        get() = super.cause?:this
    override val message: String?
        get() = super.message?:"Connection Offline"
}