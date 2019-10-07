package com.saivo.recommendo.view.viewmodel.auth

import com.saivo.recommendo.data.model.infrastructure.Client
import com.saivo.recommendo.data.model.infrastructure.Token

interface IServerAuth {
    suspend fun authInit()
    fun saveClient(client: Client)
    fun saveAccessToken(token: Token)
    suspend fun getToken(): Token
    suspend fun getClient(): Client
    suspend fun registerClient(secret: String): String
    suspend fun getAccessToken(basicAuth: String): Token
    fun addServerAuthListener(listener: IServerAuthListener)
}