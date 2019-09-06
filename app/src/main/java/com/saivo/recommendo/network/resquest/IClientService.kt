package com.saivo.recommendo.network.resquest

import com.saivo.recommendo.data.model.infrastructure.Client
import com.saivo.recommendo.data.model.infrastructure.Token
import kotlinx.coroutines.Deferred
import retrofit2.http.*

interface IClientService {

    @POST("/oauth/token")
    fun getClientAccessToken(
        @Query("email") email: String,
        @Query("password") password: String,
        @Query("grant_type") grant_type: String,
        @Header("Authorization") authentication: String
    ) : Deferred<Token>

    @POST("/clients/register")
    fun registerClientAsync(@Body clientSecret: String) : Deferred<String>

    @GET("/api/clients/{clientId}")
    fun getClientData(@Path("clientId") clientId: String) : Deferred<Client>

}