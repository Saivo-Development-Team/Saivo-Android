package com.saivo.recommendo.data.repository.client

import androidx.lifecycle.LiveData
import com.saivo.recommendo.data.model.infrastructure.Client

interface IClientRepository {
    suspend fun getClientCredentials(): LiveData<Client>
}