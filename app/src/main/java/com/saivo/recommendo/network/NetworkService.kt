package com.saivo.recommendo.network

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.saivo.recommendo.util.*
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
const val AUTHORIZATION_HEADER = "$AUTHORIZATION_TYPE $AUTHORIZATION_TOKEN"

interface NetworkService {

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

            return Retrofit.Builder().client(httpClient).baseUrl(STAGGIN_URL)
                .addCallAdapterFactory(CoroutineCallAdapterFactory())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
    }
}