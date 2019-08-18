package com.saivo.recommendo.data.repository

import androidx.lifecycle.LiveData
import com.saivo.recommendo.data.model.domain.User

interface UserRepository {
    suspend fun getUserData(): LiveData<User>
}