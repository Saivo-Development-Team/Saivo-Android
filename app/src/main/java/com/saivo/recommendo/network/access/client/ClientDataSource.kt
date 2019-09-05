package com.saivo.recommendo.network.access.client

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.saivo.recommendo.data.model.infrastructure.Client
import com.saivo.recommendo.network.resquest.IClientService
import retrofit2.Retrofit
import retrofit2.create

class ClientDataSource(private val service: Retrofit) : IClientDataSource {
    override val clientCredentials: LiveData<Client>
        get() = currentClient

    private var currentClient = MutableLiveData<Client>()

    override suspend fun registerClientAsync(clientSecret: String): String {
        return service.create<IClientService>().registerClient(clientSecret).await()
    }


}