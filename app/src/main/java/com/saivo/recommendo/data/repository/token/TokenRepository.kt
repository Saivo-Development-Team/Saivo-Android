package com.saivo.recommendo.data.repository.token

import com.saivo.recommendo.data.access.TokenDao

class TokenRepository(private val tokenDao: TokenDao) : ITokenRepository {

    private lateinit var accessToken: String
    private lateinit var tokenType: String

    override fun getAccessToken(): String {
        if (::accessToken.isInitialized) return accessToken
        return tokenDao.getAccessToken().also {
            accessToken = it
        }
    }

    override fun getTokenType(): String {
        if (::tokenType.isInitialized) return tokenType
        return tokenDao.getTokenType().also {
            tokenType = it
        }
    }
}