package com.saivo.recommendo.util.network

import android.content.Context
import android.net.ConnectivityManager
import android.net.ConnectivityManager.NetworkCallback
import android.net.Network
import android.net.NetworkRequest
import com.saivo.recommendo.util.exception.ConnectionOfflineException
import okhttp3.Interceptor
import okhttp3.Response

class Connection(context: Context) : IConnection {

    private val applicationContext = context.applicationContext
    override fun intercept(chain: Interceptor.Chain): Response {
        if (!gotInternet()) throw ConnectionOfflineException()
        return chain.proceed(chain.request())
    }

    private fun gotInternet(): Boolean {
        var connected: Boolean? = null
        val connectivityManager = applicationContext.getSystemService(
            Context.CONNECTIVITY_SERVICE
        ) as ConnectivityManager

        connectivityManager.registerNetworkCallback(
            NetworkRequest.Builder().build(),
            object : NetworkCallback() {
                override fun onAvailable(network: Network) {
                    connected = true
                    super.onAvailable(network)
                }
            })
        return connected ?: false
    }

}