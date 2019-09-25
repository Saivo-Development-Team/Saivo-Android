package com.saivo.recommendo.util.helpers

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.saivo.recommendo.BuildConfig
import com.saivo.recommendo.data.access.TokenDao
import com.saivo.recommendo.util.network.IConnection
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.Exception

inline fun <reified T> retrofitCreate(client: OkHttpClient): T {
    return Retrofit.Builder().client(client).baseUrl(getBaseUrl())
        .addCallAdapterFactory(CoroutineCallAdapterFactory())
        .addConverterFactory(GsonConverterFactory.create())
        .build().create(T::class.java)
}

inline fun <reified T> retrofit(
    auth: Boolean = false,
    connection: IConnection,
    tokenDao: TokenDao? = null
): T {
    if (auth) {
        if (tokenDao != null) return authRetrofit(connection, tokenDao)
        else throw Exception()
    }
    return retrofitCreate(httpClient(connection = connection))
}

inline fun <reified T> authRetrofit(connection: IConnection, tokenDao: TokenDao): T {
    return retrofitCreate(httpClient(authInterceptor(tokenDao), connection = connection))
}

fun httpClient(connection: IConnection): OkHttpClient {
    return OkHttpClient.Builder()
        .addInterceptor(connection)
        .build()
}

fun httpClient(interceptor: Interceptor, connection: IConnection): OkHttpClient {
    return OkHttpClient.Builder()
        .addInterceptor(interceptor)
        .addInterceptor(connection)
        .build()
}

fun authInterceptor(tokenDao: TokenDao) = Interceptor {
    return@Interceptor it.proceed(
        it.request().newBuilder()
            .url(it.request().url().newBuilder().build())
            .addHeader("Authorization", token(tokenDao))
            .build()
    )
}

fun getBaseUrl(): String {
    return if (BuildConfig.DEBUG) BuildConfig.BaseUrl
    else "https://saivo-recommendo.herokuapp.com/"
}
