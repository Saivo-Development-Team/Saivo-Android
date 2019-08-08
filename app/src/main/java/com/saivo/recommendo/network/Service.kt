package com.saivo.recommendo.network

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.saivo.recommendo.util.network.Connectivity
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

/*
* TODO:  - Find a better way to store this info
*        - AUTHORIZATION_TOKEN expires, prevent HttpException: 401, 500 etc..
*        - BASE_URL has to run on SSL
* */
const val BASE_URL = "http://10.0.2.2:8080/api/"
const val AUTHORIZATION_TYPE = "Bearer"
const val AUTHORIZATION_TOKEN = "eab7c1c6-bc15-4231-9edd-4f7cff4e8b17"
const val AUTHORIZATION_HEADER = "$AUTHORIZATION_TYPE $AUTHORIZATION_TOKEN"

interface Service {

    companion object {
        operator fun invoke(connectivity: Connectivity): Retrofit {
            val interceptor = Interceptor {
                val url = it.request().url().newBuilder().build()
                val request = it.request().newBuilder().url(url)
                    .addHeader("Authorization", AUTHORIZATION_HEADER)
                    .build()
                return@Interceptor it.proceed(request)
            }

            val httpClient = OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .addInterceptor(connectivity)
                .build()

            return Retrofit.Builder().client(httpClient).baseUrl(BASE_URL)
                .addCallAdapterFactory(CoroutineCallAdapterFactory())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
    }
}