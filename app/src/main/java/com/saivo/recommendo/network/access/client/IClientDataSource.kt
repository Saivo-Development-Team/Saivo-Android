package com.saivo.recommendo.network.access.client

import androidx.lifecycle.LiveData
import com.saivo.recommendo.data.model.infrastructure.Client

interface IClientDataSource {
    val clientCredentials: LiveData<Client>
    suspend fun registerClientAsync(clientSecret: String) : String
}