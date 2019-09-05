package com.saivo.recommendo.data.repository.user

import androidx.lifecycle.LiveData
import com.saivo.recommendo.data.model.domain.User

interface IUserRepository {
    suspend fun getUserData(): LiveData<User>
}