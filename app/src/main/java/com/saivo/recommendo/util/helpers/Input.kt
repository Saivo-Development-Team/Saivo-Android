package com.saivo.recommendo.util.helpers

enum class Input(val error: String, var inputName: String = ""){
    EMAIL(error = "Not a Valid Email"),
    PASSWORD(error = "Not a Valid Password"),
    PLAINTEXT(error = "Not Valid Text");

    val message: String = ""
}