package com.saivo.recommendo.network.resquest

import com.saivo.recommendo.data.objects.Response
import com.saivo.recommendo.data.objects.LoginCredentials
import com.saivo.recommendo.data.objects.RegisterCredentials
import kotlinx.coroutines.Deferred
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface IUserService {

    @GET("/user/{Id}")
    fun getUserDataAsync(@Path("Id") Id: String): Deferred<Response>

    @POST("/user/login")
    fun loginUserAsync(@Body loginCredentials: LoginCredentials): Deferred<Response>

    @POST("/user/register")
    fun registerUserAsync(@Body registerCredentials: RegisterCredentials): Deferred<Response>

    @POST("/user/reset-password/{email}")
    fun restUserPasswordAsync(@Body password: String, @Path("email") email: String) : Deferred<Response>

    @POST("/user/otp/{email}")
    fun getOTPFromServerAsync(@Body number: String, @Path("email") email: String): Deferred<Response>
}