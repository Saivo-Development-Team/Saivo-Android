package com.saivo.recommendo.util.network

import android.content.Context
import android.net.ConnectivityManager
import com.saivo.recommendo.util.exception.ConnectionOfflineException
import okhttp3.Interceptor
import okhttp3.Response

class Connection(context: Context) : Connectivity {
    private val applicationContext = context.applicationContext
    override fun intercept(chain: Interceptor.Chain): Response {
        if (!gotInternet()){
            throw ConnectionOfflineException()
        }
        return chain.proceed(chain.request())
    }

    private fun gotInternet(): Boolean {
        val connectivityManager = applicationContext.getSystemService(
            Context.CONNECTIVITY_SERVICE
        ) as ConnectivityManager
        val nesting = connectivityManager.activeNetworkInfo // TODO - Read Network Access API Docs - This may not be needed
        return (nesting != null && nesting.isConnected) || connectivityManager.isDefaultNetworkActive // TODO - Read API
    }

}