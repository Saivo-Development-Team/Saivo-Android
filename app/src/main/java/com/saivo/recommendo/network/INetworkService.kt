package com.saivo.recommendo.network

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.saivo.recommendo.BuildConfig
import com.saivo.recommendo.util.network.IConnection
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

interface INetworkService {

    companion object {

        inline operator fun <reified T> invoke(
            connection: IConnection
        ): T {
            val interceptor = Interceptor {
                val url = it.request().url().newBuilder().build()
                val request = it.request().newBuilder().url(url)
                    .addHeader("Authorization", BuildConfig.BearerToken)
                    .build()
                return@Interceptor it.proceed(request)
            }

            val httpClient = OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .addInterceptor(connection)
                .build()

            return Retrofit.Builder().client(httpClient).baseUrl(BuildConfig.BaseUrl)
                .addCallAdapterFactory(CoroutineCallAdapterFactory())
                .addConverterFactory(GsonConverterFactory.create())
                .build().create(T::class.java)
        }
    }
}