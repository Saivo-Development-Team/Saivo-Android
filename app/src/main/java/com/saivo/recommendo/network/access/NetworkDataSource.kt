package com.saivo.recommendo.network.access

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.saivo.recommendo.data.model.infrastructure.UserData
import com.saivo.recommendo.data.objects.LoginCredentials
import com.saivo.recommendo.data.objects.RegisterCredentials
import com.saivo.recommendo.data.objects.Response
import com.saivo.recommendo.network.NetworkService
import com.saivo.recommendo.network.resquest.ApiUserService
import com.saivo.recommendo.util.network.Connection
import retrofit2.HttpException

class NetworkDataSource(
    private val context: Context
) : DataSource {

    private val currentUserData = MutableLiveData<UserData>()

    override val userData: LiveData<UserData>
        get() = currentUserData

    override suspend fun getUserDataAsync(Id: String): Response? {
        try {
            return NetworkService.invoke(Connection(context))
                .create(ApiUserService::class.java)
                .getUserDataAsync(Id).await()
        } catch (e: HttpException) {
            Log.e("Network", e.message())
        }
        return null
    }

    override suspend fun loginUserAsync(loginCredentials: LoginCredentials): Response? {
        try {
            val response = NetworkService.invoke(Connection(context))
                .create(ApiUserService::class.java)
                .loginUserAsync(loginCredentials).await()
            if (response.data != null) {
                currentUserData.value = response.getObjectFromData(UserData::class.java)
            }
            return response
        } catch (e: HttpException) { // TODO - Try to catch this sooner with Connectivity: Interceptor
            Log.e("Network", e.message())
        }
        return null
    }

    override suspend fun registerUserAsync(registerCredentials: RegisterCredentials): Response? {
        try {
            return NetworkService.invoke(Connection(context))
                .create(ApiUserService::class.java)
                .registerUserAsync(registerCredentials).await()
        } catch (e: HttpException) {
            Log.e("Network", e.message())
        }
        return null
    }
}