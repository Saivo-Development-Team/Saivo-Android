package com.saivo.recommendo.network.access.user

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.saivo.recommendo.data.model.infrastructure.UserData
import com.saivo.recommendo.data.objects.LoginCredentials
import com.saivo.recommendo.data.objects.RegisterCredentials
import com.saivo.recommendo.data.objects.Response
import com.saivo.recommendo.network.resquest.IUserService
import retrofit2.HttpException
import retrofit2.Retrofit
import retrofit2.create
import java.net.SocketTimeoutException

class UserDataSource(
    private val service: Retrofit
) : IUserDataSource {
    private val currentUserData = MutableLiveData<UserData>()

    override val userData: LiveData<UserData>
        get() = currentUserData

    override suspend fun getUserDataAsync(Id: String): Response {
        var response = Response()
        try {
            response = service.create<IUserService>()
                .getUserDataAsync(Id).await()
            if (response.data != null) {
                currentUserData.postValue(response.getObjectFromData(UserData::class.java))
            }
        } catch (e: HttpException) {
            Log.e("Network", "[${e.cause}]: ${e.message!!}")
        }
        return response
    }

    override suspend fun loginUserAsync(credentials: LoginCredentials): Response {
        var response = Response()
        try {
            response = service.create<IUserService>()
                .loginUserAsync(credentials).await()
            if (response.data != null) {
                currentUserData.postValue(response.getObjectFromData(UserData::class.java))
            }
        } catch (e: Exception) {
            Log.e("Network", "[${e.cause}]: ${e.message!!}")
            when (e) {
                is HttpException -> {
                    Log.e("Network", e.message!!)
                    response = Response(
                        error = "HttpException",
                        status = "NETWORK_ERROR",
                        message = "Check Internet access"
                    )
                }
                is SocketTimeoutException -> {
                    Log.e("Timeout", e.message!!)
                    response = Response(
                        error = "SocketTimeoutException",
                        status = "TIMEOUT_ERROR",
                        message = "Server is down at the moment"
                    )
                }
            }
        }
        return response
    }

    override suspend fun registerUserAsync(credentials: RegisterCredentials): Response {
        try {
            return service.create<IUserService>()
                .registerUserAsync(credentials).await()
        } catch (e: Exception) {
            Log.e("Network", "[${e.cause}]: ${e.message!!}")
        }
        return Response()
    }
}