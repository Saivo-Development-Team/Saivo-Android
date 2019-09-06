package com.saivo.recommendo.network.access.token

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.saivo.recommendo.data.model.infrastructure.Token
import com.saivo.recommendo.network.resquest.ITokenService

class TokenDataSource(private val tokenService: ITokenService) : ITokenDataSource {
    override val token: LiveData<Token>
        get() = fetchedToken

    private val fetchedToken = MutableLiveData<Token>()

    override suspend fun getTokenWithClient(grant_type: String, authentication: String) {
        tokenService.getTokenWithClientAsync(grant_type, authentication)
            .await().let {
                fetchedToken.postValue(it)
            }
    }

    override suspend fun getTokenWithUser(
        email: String,
        password: String,
        grant_type: String,
        authentication: String
    ) {
        tokenService.getTokenWithUserAsync(email, password, grant_type, authentication)
            .await().let {
                fetchedToken.postValue(it)
            }
    }
}