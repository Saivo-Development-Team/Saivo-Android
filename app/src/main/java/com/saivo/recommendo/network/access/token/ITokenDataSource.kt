package com.saivo.recommendo.network.access.token

import androidx.lifecycle.LiveData
import com.saivo.recommendo.data.model.infrastructure.Token

interface ITokenDataSource {
    val token: LiveData<Token>
    suspend fun getTokenWithClient(
        grant_type: String = "client_credentials",
        authentication: String
    )

    suspend fun getTokenWithUser(
        email: String,
        password: String,
        grant_type: String,
        authentication: String
    )
}