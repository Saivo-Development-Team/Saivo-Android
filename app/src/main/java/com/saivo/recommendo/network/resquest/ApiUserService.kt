package com.saivo.recommendo.network.resquest

import com.saivo.recommendo.data.model.domain.User
import com.saivo.recommendo.data.objects.Login
import kotlinx.coroutines.Deferred
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiUserService {

    @GET("/api/users")
    fun getUsersAsync(): Deferred<List<User>>

    @GET("/api/users/{id}")
    fun getUserAsync(@Path("Id") id: String): Deferred<User>

    @POST("/users/login")
    fun loginUserAsync(@Body login: Login): Deferred<User>

    @POST("/api/users")
    fun registerUserAsync(@Body user: User): Deferred<String>

}