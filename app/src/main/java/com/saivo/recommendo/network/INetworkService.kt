package com.saivo.recommendo.network

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.saivo.recommendo.data.access.TokenDao
import com.saivo.recommendo.data.repository.token.ITokenRepository
import com.saivo.recommendo.util.DEVELOPMENT_URL
import com.saivo.recommendo.util.DEVELOPMENT_URL_WIFI
import com.saivo.recommendo.util.STAGGING_URL
import com.saivo.recommendo.util.network.IConnection
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/*
* TODO:  - Find a better way to store this info
*        - AUTHORIZATION_TOKEN expires, prevent HttpException: 401, 500 etc..
*        - BASE_URL has to run on SSL
* */

interface INetworkService {

    companion object {

        private lateinit var authorizationHeader: String

        inline operator fun <reified T> invoke(
            connection: IConnection
        ): T {
            val interceptor = Interceptor {
                val url = it.request().url().newBuilder().build()
                val request = it.request().newBuilder().url(url)
                    .addHeader("Authorization", "Bearer 294b14ec-9c1b-47d4-815b-c763ab005408")
                    .build()
                return@Interceptor it.proceed(request)
            }

            val httpClient = OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .addInterceptor(connection)
                .build()

            return Retrofit.Builder().client(httpClient).baseUrl(STAGGING_URL)
                .addCallAdapterFactory(CoroutineCallAdapterFactory())
                .addConverterFactory(GsonConverterFactory.create())
                .build().create(T::class.java)
        }

//        fun getHeaderValues(tokenDao: TokenDao): String {
//            if (::authorizationHeader.isInitialized) {
//                return authorizationHeader
//            }
//
//            setHeaderValues(type = tokenDao.getTokenType(), authorization = tokenDao.getAccessToken())
//            return authorizationHeader
//        }
//
//        private fun setHeaderValues(type: String = "", authorization: String) {
//            authorizationHeader = if (type.isNotBlank()) {
//                "$type $authorization"
//            } else {
//                "Bearer $authorization"
//            }
//        }
    }
}