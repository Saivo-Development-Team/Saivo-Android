package com.saivo.recommendo.data.model.infrastructure

import com.saivo.recommendo.data.model.domain.Preference
import com.saivo.recommendo.data.model.domain.User

data class UserData(
    val user: String = "",
    val email: String = "",
    val enabled: Boolean = true,
    val lastname: String = "",
    val firstname: String = ""
) {
    fun getUserDetailsFromData(): User {
        return User(
            user = user,
            email = email,
            enabled = enabled,
            lastname = lastname,
            firstname = firstname
        )
    }
}