package com.saivo.recommendo.network.resquest

import com.saivo.recommendo.data.objects.Response
import com.saivo.recommendo.data.objects.LoginCredentials
import com.saivo.recommendo.data.objects.RegisterCredentials
import kotlinx.coroutines.Deferred
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiUserService {

    @GET("/api/users/{Id}")
    fun getUserDataAsync(@Path("Id") Id: String): Deferred<Response>

    @POST("/users/login")
    fun loginUserAsync(@Body loginCredentials: LoginCredentials): Deferred<Response>

    @POST("/users/register")
    fun registerUserAsync(@Body registerCredentials: RegisterCredentials): Deferred<Response>

}