package com.saivo.recommendo.network.resquest

import com.saivo.recommendo.data.model.infrastructure.Client
import com.saivo.recommendo.data.model.infrastructure.Token
import com.saivo.recommendo.data.objects.Response
import kotlinx.coroutines.Deferred
import retrofit2.http.*

interface IClientService {

    @POST("/clients/register")
    fun registerClientAsync(@Body clientSecret: String) : Deferred<Response>

    @GET("/api/clients/{clientId}")
    fun getClientData(@Path("clientId") clientId: String) : Deferred<Response>

}