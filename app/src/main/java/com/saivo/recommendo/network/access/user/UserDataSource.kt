package com.saivo.recommendo.network.access.user

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.saivo.recommendo.data.model.infrastructure.UserData
import com.saivo.recommendo.data.objects.LoginCredentials
import com.saivo.recommendo.data.objects.RegisterCredentials
import com.saivo.recommendo.data.objects.Response
import com.saivo.recommendo.network.resquest.IUserService
import com.saivo.recommendo.util.helpers.handleNullResponse
import com.saivo.recommendo.util.helpers.logPrintStackTrace

class UserDataSource(
    private val userService: IUserService
) : IUserDataSource {
    private val fetchedUserData = MutableLiveData<UserData>()

    override val userData: LiveData<UserData>
        get() = fetchedUserData

    override suspend fun getUserDataAsync(Id: String): Response {
        return runCatching {
            userService.getUserDataAsync(Id).await()
        }.onFailure {
            it.logPrintStackTrace("getUserDataAsync(Id: String)")
        }.onSuccess {
            it.handleNullResponse {
                fetchedUserData.postValue(this.getObject())
            }
        }.getOrDefault(Response())
    }

    override suspend fun loginUserAsync(credentials: LoginCredentials): Response {
        return runCatching {
            userService.loginUserAsync(credentials).await()
        }.onFailure {
            it.logPrintStackTrace("loginUserAsync(credentials)")
        }.onSuccess {
            it.handleNullResponse {
                fetchedUserData.postValue(this.getObject())
            }
        }.getOrDefault(Response())
    }

    override suspend fun registerUserAsync(credentials: RegisterCredentials): Response {
        return runCatching {
            userService.registerUserAsync(credentials).await()
        }.onFailure {
            it.logPrintStackTrace("registerUserAsync(credentials: RegisterCredentials)")
        }.getOrDefault(Response())
    }

    override suspend fun restUserPassword(password: String, email: String) {
        runCatching {
            userService.restUserPasswordAsync(password, email).await()
        }.onFailure {
            it.logPrintStackTrace("restUserPassword(password: String, email: String)")
        }.getOrDefault(Response())
    }

    override suspend fun getOTPFromServer(number: String, email: String): Response {
        return runCatching {
            userService.getOTPFromServerAsync(number, email).await()
        }.onFailure {
            it.logPrintStackTrace("getOTPFromServerAsync(number, email)")
        }.getOrDefault(Response())
    }
}