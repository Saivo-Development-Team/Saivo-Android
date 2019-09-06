package com.saivo.recommendo.data.repository.token

import android.util.Base64
import android.util.Log
import com.saivo.recommendo.data.access.ClientDao
import com.saivo.recommendo.data.access.TokenDao
import com.saivo.recommendo.network.access.token.ITokenDataSource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TokenRepository(
    private val tokenDao: TokenDao,
    private val clientDao: ClientDao,
    private val tokenDataSource: ITokenDataSource
) : ITokenRepository {

    private lateinit var accessToken: String
    private lateinit var tokenType: String

    init {
        CoroutineScope(Dispatchers.IO).launch {
            with(tokenDataSource) {
                token.observeForever {
                    Log.i("Token", "[init Token] : ${token.value?.accessToken.orEmpty()}")
                }
            }
        }
    }

    override fun getAccessToken(): String {
        if (::accessToken.isInitialized) return accessToken
        return tokenDao.getAccessToken().also {
            if (it.isBlank()) {
                CoroutineScope(Dispatchers.IO).launch {
                    with(tokenDataSource) {
                        getTokenWithClient(authentication = "Basic ${getBase64Credentials()}")
                        Log.i("Token", "[Got Token] : ${token.value?.accessToken.orEmpty()}")
                    }
                }
            }
        }
    }

    override fun getTokenType(): String {
        if (::tokenType.isInitialized) return tokenType
        return tokenDao.getTokenType().also {
            tokenType = it
        }
    }

    private fun getBase64Credentials(): String {
        clientDao.getClientCredentials().apply {
            return Base64.encodeToString(("$clientId:$clientSecret").toByteArray(), Base64.URL_SAFE)
        }
    }
}