package com.saivo.recommendo.network.access

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.saivo.recommendo.data.model.domain.User
import com.saivo.recommendo.data.objects.Login
import com.saivo.recommendo.network.Service
import com.saivo.recommendo.network.resquest.ApiUserService
import com.saivo.recommendo.util.exception.ConnectionOfflineException
import com.saivo.recommendo.util.network.Connection
import retrofit2.HttpException
import retrofit2.create

class DataSource (
    private val context: Context
) : NetworkData {

    private val currentUserData = MutableLiveData<User>()
    override val userData: LiveData<User>
        get() = currentUserData

    override suspend fun getUserDataAsync(id: String) {
        try {
            val user = Service.invoke(Connection(context))
                .create<ApiUserService>()
                .getUserAsync(id).await()
            currentUserData.postValue(user)
        } catch (e : ConnectionOfflineException) {
            Log.e("Connection", "Network Seems to be Down")
        }
    }

    override suspend fun loginUserAsync(login: Login) {
        try {
            val user = Service.invoke(Connection(context))
                .create(ApiUserService::class.java)
                .loginUserAsync(login).await()
            currentUserData.postValue(user)
        } catch (e: HttpException) { // TODO - Try to catch this sooner with Connectivity: Interceptor
            Log.e("Network", e.message())
        }
    }
}