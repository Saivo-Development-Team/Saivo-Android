package com.saivo.recommendo.network.resquest

import com.saivo.recommendo.data.model.infrastructure.Token
import kotlinx.coroutines.Deferred
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Query

interface ITokenService {

    @POST("/oauth/token")
    fun getTokenByClientAsync(
        @Query("grant_type") grant_type: String,
        @Header("Authorization") authentication: String
    ) : Deferred<Token>

    @POST("/oauth/token")
    fun getTokenByUserAsync(
        @Query("email") email: String,
        @Query("password") password: String,
        @Query("grant_type") grant_type: String,
        @Header("Authorization") authentication: String
    ) : Deferred<Token>
}