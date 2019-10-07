package com.saivo.recommendo.util.network

import com.saivo.recommendo.data.access.ClientDao
import com.saivo.recommendo.data.model.infrastructure.Client

interface IClient {
    companion object {
        suspend operator fun invoke(
            clientDao: ClientDao,
            createClientCallback: suspend (clientDao: ClientDao) -> Client
        ): Client {
            return clientDao.getClient() ?: createClientCallback(clientDao)
        }
    }
}