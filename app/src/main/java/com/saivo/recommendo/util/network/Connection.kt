package com.saivo.recommendo.util.network

import android.content.Context
import android.net.ConnectivityManager
import com.saivo.recommendo.util.exception.ConnectionOfflineException
import okhttp3.Interceptor
import okhttp3.Response

class Connection(context: Context) : IConnection {

    private val applicationContext = context.applicationContext
    override fun intercept(chain: Interceptor.Chain): Response {
        return runCatching {
            if (!gotInternet()) throw ConnectionOfflineException()
            chain.proceed(chain.request())
        }.getOrThrow()
    }

    private fun gotInternet(): Boolean {
        val connectivityManager = applicationContext.getSystemService(
            Context.CONNECTIVITY_SERVICE
        ) as ConnectivityManager
        val nesting = connectivityManager.activeNetworkInfo
        return (nesting != null && nesting.isConnected) || connectivityManager.isDefaultNetworkActive
    }
}