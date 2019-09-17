package com.saivo.recommendo.util.network

import com.saivo.recommendo.data.access.TokenDao
import com.saivo.recommendo.data.model.infrastructure.Client

interface IToken {
    companion object {
        suspend operator fun invoke(
            tokenDao: TokenDao,
            client: Client,
            createTokenCallback: suspend (client: Client, tokenDao: TokenDao) -> String
        ): String {
            return tokenDao.getAccessToken() ?: createTokenCallback(client, tokenDao)
        }
    }
}