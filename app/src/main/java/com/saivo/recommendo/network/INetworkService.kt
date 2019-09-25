package com.saivo.recommendo.network

import com.saivo.recommendo.data.access.TokenDao
import com.saivo.recommendo.util.helpers.retrofit
import com.saivo.recommendo.util.network.IConnection

interface INetworkService {

    companion object {
        inline operator fun <reified T> invoke(
            connection: IConnection,
            tokenDao: TokenDao
        ): T {
            return retrofit(true, connection, tokenDao)
        }
    }
}