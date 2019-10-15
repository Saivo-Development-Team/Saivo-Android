package com.saivo.recommendo.network.access.user

import androidx.lifecycle.LiveData
import com.saivo.recommendo.data.model.infrastructure.UserData
import com.saivo.recommendo.data.objects.LoginCredentials
import com.saivo.recommendo.data.objects.RegisterCredentials
import com.saivo.recommendo.data.objects.Response

interface IUserDataSource {
    val userData: LiveData<UserData>
    suspend fun getUserDataAsync(Id: String): Response
    suspend fun loginUserAsync(credentials: LoginCredentials) : Response
    suspend fun registerUserAsync(credentials: RegisterCredentials) : Response
    suspend fun restUserPassword(password: String, email: String)
    suspend fun getOTPFromServer(number: String, email: String): Response
}