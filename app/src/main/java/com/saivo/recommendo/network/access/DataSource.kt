package com.saivo.recommendo.network.access

import androidx.lifecycle.LiveData
import com.saivo.recommendo.data.model.infrastructure.UserData
import com.saivo.recommendo.data.objects.LoginCredentials
import com.saivo.recommendo.data.objects.RegisterCredentials
import com.saivo.recommendo.data.objects.Response

interface DataSource {
    val userData: LiveData<UserData>
    suspend fun getUserDataAsync(Id: String): Response?
    suspend fun loginUserAsync(loginCredentials: LoginCredentials) : Response?
    suspend fun registerUserAsync(registerCredentials: RegisterCredentials) : Response?
}