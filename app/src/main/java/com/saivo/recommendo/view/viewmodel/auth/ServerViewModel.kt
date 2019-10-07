package com.saivo.recommendo.view.viewmodel.auth

import android.util.Log
import androidx.lifecycle.ViewModel
import com.saivo.recommendo.data.access.ClientDao
import com.saivo.recommendo.data.access.TokenDao
import com.saivo.recommendo.data.model.infrastructure.Client
import com.saivo.recommendo.data.model.infrastructure.Token
import com.saivo.recommendo.network.resquest.IClientService
import com.saivo.recommendo.network.resquest.ITokenService
import com.saivo.recommendo.util.helpers.basicAuth
import com.saivo.recommendo.util.helpers.createUUID
import com.saivo.recommendo.util.helpers.retrofit
import com.saivo.recommendo.util.network.IConnection
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext

class ServerViewModel(
    private val tokenDao: TokenDao,
    private val clientDao: ClientDao,
    private val connection: IConnection
) : ViewModel(), IServerAuth {
    private val secret = createUUID()
    private lateinit var client: Client
    private lateinit var authListener: IServerAuthListener

    override suspend fun authInit() {
        getListenerOrNull().onInit()
        getClient()
    }

    override fun saveClient(client: Client) {
        return clientDao.updateClient(client)
    }

    override suspend fun getClient(): Client {
        return withContext(IO) {
            clientDao.getClient() ?: createClient(registerClient(secret), secret)
        }.apply {
            client = this
        }
    }

    override suspend fun getToken(): Token {
        return withContext(IO) {
            tokenDao.getToken() ?: getAccessToken(
                basicAuth(client.clientId, client.clientSecret)
            ).apply { saveAccessToken(this) }
        }
    }

    override fun saveAccessToken(token: Token) {
        tokenDao.updateTokenData(token)
    }

    override suspend fun registerClient(secret: String): String {
        getListenerOrNull().onRegister()
        return CoroutineScope(IO).async {
            return@async retrofit<IClientService>(connection = connection)
                .registerClientAsync(secret).await()
        }.await().data as String
    }

    override suspend fun getAccessToken(basicAuth: String): Token {
        getListenerOrNull().apply {
            onCreateToken { (Log.i("TOKEN", "Create Token Started")) }
            return withContext(IO) {
                retrofit<ITokenService>(connection = connection).getTokenByClientAsync(
                    authentication = basicAuth, grant_type = "client_credentials"
                ).await()
            }.apply {
                onTokenCreated { (Log.i("TOKEN", "Token Created [$accessToken]")) }
            }
        }
    }

    private fun createClient(Id: String, secret: String): Client {
        return Client(clientId = Id, clientSecret = secret).apply {
            saveClient(this)
        }
    }

    override fun addServerAuthListener(listener: IServerAuthListener) {
        authListener = listener
    }

    private fun getListenerOrNull(): IServerAuthListener {
        return this.authListener
    }
}