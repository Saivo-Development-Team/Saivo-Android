package com.saivo.recommendo.network

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.saivo.recommendo.data.repository.token.ITokenRepository
import com.saivo.recommendo.util.DEVELOPMENT_URL_WIFI
import com.saivo.recommendo.util.network.IConnectivity
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

        operator fun invoke(connectivity: IConnectivity, tokenRepository: ITokenRepository): Retrofit {

            fun getHeaderValues(): String {
                if (::authorizationHeader.isInitialized) {
                    return authorizationHeader
                }

                setHeaderValues(authorization = tokenRepository.getAccessToken())
                return authorizationHeader
            }

            val interceptor = Interceptor {
                val url = it.request().url().newBuilder().build()
                val request = it.request().newBuilder().url(url)
                    .addHeader("Authorization", getHeaderValues())
                    .build()
                return@Interceptor it.proceed(request)
            }

            val httpClient = OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .addInterceptor(connectivity)
                .build()

            return Retrofit.Builder().client(httpClient).baseUrl(DEVELOPMENT_URL_WIFI)
                .addCallAdapterFactory(CoroutineCallAdapterFactory())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }

        private fun setHeaderValues(type: String = "", authorization: String) {
            authorizationHeader = if (type.isNotBlank()) {
                "$type $authorization"
            } else {
                "Bearer $authorization"
            }
        }
    }
}