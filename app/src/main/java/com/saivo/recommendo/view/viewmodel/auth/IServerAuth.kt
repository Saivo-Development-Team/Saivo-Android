package com.saivo.recommendo.view.viewmodel.auth

import com.saivo.recommendo.data.model.infrastructure.Client
import com.saivo.recommendo.data.model.infrastructure.Token

interface IServerAuth {
    suspend fun init()
    suspend fun getClient(): Client
    suspend fun saveClient(client: Client)
    fun saveAccessToken(token: Token?)
    suspend fun initAuth()
    suspend fun getAccessToken(): Token?
    suspend fun isValid(token: Token): Boolean
    suspend fun registerClient(secret: String): String
    fun addServerAuthListener(listener: IServerAuthListener)
}