package com.saivo.recommendo.network.resquest

import com.saivo.recommendo.data.model.User
import com.saivo.recommendo.network.ApiService
import kotlinx.coroutines.Deferred
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiUserService {

    @GET("/api/users")
    fun getUsers(): List<User>

    @POST("/api/users")
    fun registerUser(@Body user: User): Deferred<String>

}