package com.saivo.recommendo.network

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.saivo.recommendo.data.model.ClientAccessToken
import com.saivo.recommendo.data.model.User
import com.saivo.recommendo.network.resquest.ApiUserService
import kotlinx.coroutines.Deferred
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*

const val BASE_URL = "https://saivo-recommendo.herokuapp.com/api/"
const val AUTHORIZATION_TYPE = "Bearer"
const val AUTHORIZATION_TOKEN = "e986fb0d-6775-4e86-ae2b-fc34e4cd8266"
const val AUTHORIZATION_HEADER = "$AUTHORIZATION_TYPE $AUTHORIZATION_TOKEN"

interface ApiService {

    @POST("/api/users")
    fun registerUserAsync(@Body user: User): Deferred<String>

    @GET("/api/users")
    fun getUsersAsync(): Deferred<List<User>>

    companion object {
        operator fun invoke(): ApiService {
            val interceptor = Interceptor {
                val url = it.request().url().newBuilder().build()
                val request = it.request().newBuilder().url(url)
                    .addHeader("Authorization", AUTHORIZATION_HEADER)
                    .build()
                return@Interceptor it.proceed(request)
            }

            val httpClient = OkHttpClient.Builder().addInterceptor(interceptor).build()

            return Retrofit.Builder().client(httpClient).baseUrl(BASE_URL)
                .addCallAdapterFactory(CoroutineCallAdapterFactory())
                .addConverterFactory(GsonConverterFactory.create())
                .build().create(ApiService::class.java)
        }
    }
}