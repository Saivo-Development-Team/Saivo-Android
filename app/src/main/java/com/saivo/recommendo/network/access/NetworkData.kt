package com.saivo.recommendo.network.access

import androidx.lifecycle.LiveData
import com.saivo.recommendo.data.model.domain.User
import com.saivo.recommendo.data.objects.Login

interface NetworkData {
    val userData: LiveData<User>

    suspend fun getUserDataAsync(id: String)

    suspend fun loginUserAsync(login: Login)

}