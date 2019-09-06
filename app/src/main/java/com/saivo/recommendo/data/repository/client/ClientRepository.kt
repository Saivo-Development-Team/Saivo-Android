package com.saivo.recommendo.data.repository.client

import at.favre.lib.crypto.bcrypt.BCrypt
import com.saivo.recommendo.data.access.ClientDao
import com.saivo.recommendo.data.model.infrastructure.Client
import com.saivo.recommendo.network.access.client.IClientDataSource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*

class ClientRepository(
    private val clientDao: ClientDao,
    private val clientDataSource: IClientDataSource
) : IClientRepository {

    init {
        CoroutineScope(IO).launch {

        }
    }

    override fun getClientCredentials(): Client {
        return clientDao.getClientCredentials()
    }

    private suspend fun createClientCredentials(): Client {
        return withContext(IO) {
            return@withContext Client(
                clientSecret = BCrypt.withDefaults().hashToString(
                    BCrypt.MIN_COST,
                    UUID.randomUUID().toString().toCharArray()
                )
            ).apply {
                clientId = clientDataSource.registerClientAsync(clientSecret)
            }
        }
    }

    private suspend fun saveClientCredentials(client: Client) {
        withContext(IO) {
            clientDao.updateClientCredentials(client)
        }
    }
}