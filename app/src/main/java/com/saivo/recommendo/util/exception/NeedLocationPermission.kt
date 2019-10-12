package com.saivo.recommendo.util.exception

class NeedLocationPermission : Throwable() {
    override val cause: Throwable?
        get() = super.cause ?: this
    override val message: String?
        get() = super.message ?: "Location Permissions not Granted"
}
