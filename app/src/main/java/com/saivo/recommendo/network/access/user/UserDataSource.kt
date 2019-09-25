package com.saivo.recommendo.network.access.user

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.saivo.recommendo.data.model.infrastructure.UserData
import com.saivo.recommendo.data.objects.LoginCredentials
import com.saivo.recommendo.data.objects.RegisterCredentials
import com.saivo.recommendo.data.objects.Response
import com.saivo.recommendo.network.access.IUserDataSource
import com.saivo.recommendo.network.resquest.IUserService
import com.saivo.recommendo.util.exception.ConnectionOfflineException
import retrofit2.HttpException
import java.net.SocketTimeoutException

class UserDataSource(
    private val userService: IUserService
) : IUserDataSource {
    private val fetchedUserData = MutableLiveData<UserData>()

    override val userData: LiveData<UserData>
        get() = fetchedUserData

    override suspend fun getUserDataAsync(Id: String): Response {
        var response = Response()
        try {
            response = userService.getUserDataAsync(Id).await()
            if (response.data != null) {
                fetchedUserData.postValue(response.getObjectFromData(UserData::class.java))
            }
        } catch (e: HttpException) {
            Log.e("Network", "[${e.cause}]: ${e.message!!}")
        }
        return response
    }

    override suspend fun loginUserAsync(credentials: LoginCredentials): Response {
        var response: Response
        try {
            response = userService.loginUserAsync(credentials).await()
            if (response.data != null) {
                fetchedUserData.postValue(response.getObjectFromData(UserData::class.java))
            }
        } catch (e: Exception) {
            Log.e("Network", "[${e.cause}]: ${e.message!!}")
            when (e) {
                is ConnectionOfflineException -> {
                    Log.e("Connection", e.message!!)
                    response = Response(
                        error = "ConnectionOfflineException",
                        status = "NETWORK_ERROR",
                        message = "Check Internet access"
                    )
                    e.printStackTrace()
                }
                is SocketTimeoutException -> {
                    Log.e("Timeout", e.message!!)
                    response = Response(
                        error = "SocketTimeoutException",
                        status = "TIMEOUT_ERROR",
                        message = "Server is down at the moment"
                    )
                }
                else -> {
                    Log.e("Error", e.message.toString())
                    response = Response(
                        error = e.cause.toString(),
                        status = "UNKNOWN_ERROR",
                        message = "Looks like we missed this error"
                    )
                }
            }
        }
        return response
    }

    override suspend fun registerUserAsync(credentials: RegisterCredentials): Response {
        try {
            return userService.registerUserAsync(credentials).await()
        } catch (e: Exception) {
            Log.e("Network", "[${e.cause}]: ${e.message!!}")
        }
        return Response()
    }
}