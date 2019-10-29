package com.saivo.recommendo.data.model.infrastructure
import com.saivo.recommendo.data.model.domain.User

data class UserData(
    val id: String,
    val email: String,
    val enabled: Boolean,
    val lastname: String,
    val firstname: String
) {
    fun getUserDetailsFromData(): User {
        return User(
            user = id,
            email = email,
            enabled = enabled,
            lastname = lastname,
            firstname = firstname
        )
    }
}