package com.saivo.recommendo.network.resquest

import com.saivo.recommendo.data.model.infrastructure.AccessToken
import kotlinx.coroutines.Deferred
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Query

interface ApiClientService {

    @POST("/oauth/token")
    fun getClientAccessToken(
        @Query("email") email: String,
        @Query("password") password: String,
        @Query("grant_type") grant_type: String,
        @Header("Authorization") authentication: String
    ) : Deferred<AccessToken>

    @POST("/register/client")
    fun registerClient()

}