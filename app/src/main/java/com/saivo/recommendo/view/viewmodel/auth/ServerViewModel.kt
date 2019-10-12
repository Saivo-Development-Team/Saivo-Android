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
import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers.IO

class ServerViewModel(
        private val tokenDao: TokenDao,
        private val clientDao: ClientDao,
        private val connection: IConnection
) : ViewModel(), IServerAuth {
    private val secret = createUUID()
    private lateinit var client: Client
    private lateinit var authListener: IServerAuthListener

    override suspend fun init() {
        getListenerOrNull().onInit()
        initAuth()
    }

    private fun auth() = with(getClient()) { basicAuth(clientId, clientSecret) }

    override suspend fun saveClient(client: Client) = withContext(IO) {
        clientDao.updateClient(client)
    }

    override fun getClient(): Client {
        runBlocking { client = client() }
        getListenerOrNull().onAccess()
        return client
    }

    private suspend fun client() = withContext(IO) {
        clientDao.getClient() ?: createClient(registerClient(secret), secret)
    }

    private suspend fun token() = withContext(IO) {
        tokenDao.getToken().apply {
            if (this == null) saveAccessToken(getAccessToken())
            else isValidToken(this).let {
                if (!it) saveAccessToken(getAccessToken())
            }
        }
    }

    override suspend fun initAuth() {
        runCatching {
            token()
        }.onFailure {
            createClient(registerClient(secret), secret)
            initAuth()
        }
    }

    override suspend fun isValidToken(token: Token): Boolean {
        runCatching {
            retrofit<ITokenService>(connection = connection).checkTokenAsync(
                    token.accessToken, auth()
            ).await().also { println(it) }
        }.onFailure {
            it.printStackTrace()
        }.onSuccess {
            return true
        }
        return false
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

    override suspend fun getAccessToken(): Token {
        getListenerOrNull().apply {
            return withContext(IO) {
                onCreateToken { (Log.i("TOKEN", "Creating Token")) }
                retrofit<ITokenService>(connection = connection).getTokenByClientAsync(
                        authentication = auth(), grant_type = "client_credentials"
                ).await()
            }.apply {
                onTokenCreated { (Log.i("TOKEN", "Token Created")) }
            }
        }
    }

    private fun createClient(Id: String, secret: String): Client {
        return Client(clientId = Id, clientSecret = secret).also { newClient ->
            CoroutineScope(IO).launch {
                saveClient(newClient)
            }
        }
    }

    override fun addServerAuthListener(listener: IServerAuthListener) {
        authListener = listener
    }

    private fun getListenerOrNull(): IServerAuthListener {
        return this.authListener
    }
}