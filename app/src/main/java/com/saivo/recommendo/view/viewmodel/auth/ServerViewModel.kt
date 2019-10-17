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
import com.saivo.recommendo.util.helpers.logPrintStackTrace
import com.saivo.recommendo.util.helpers.retrofit
import com.saivo.recommendo.util.network.IConnection
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ServerViewModel(
    private val tokenDao: TokenDao,
    private val clientDao: ClientDao,
    private val connection: IConnection
) : ViewModel(), IServerAuth {
    private val secret = createUUID()
    private lateinit var authListener: IServerAuthListener

    override suspend fun init() {
        initAuth()
    }

    private suspend fun auth() = with(getClient()) { basicAuth(clientId, clientSecret) }

    override suspend fun saveClient(client: Client) = withContext(IO) {
        clientDao.updateClient(client)
    }

    override suspend fun getClient(): Client {
        return runCatching {
            with(client()) {
                clientId.let {
                    return when {
                        it.isNotBlank() -> client()
                        else -> register(this)
                    }
                }
            }
        }.onSuccess {
            authListener().onAccess()
        }.getOrThrow()
    }

    private suspend fun register(client: Client): Client {
        return createClient(registerClient(client.clientSecret), client.clientSecret)
    }

    private suspend fun client() = withContext(IO) {
        clientDao.getClient() ?: createClient(registerClient(secret), secret)
    }

    private suspend fun token() = withContext(IO) {
        tokenDao.getToken().apply {
            if (this == null) saveAccessToken(getAccessToken())
            else isValid(this).let {
                if (!it) saveAccessToken(getAccessToken())
            }
        }
    }

    override suspend fun initAuth() {
        runCatching {
            authListener().onInit()
            token()
        }.onFailure {
            createClient(registerClient(secret), secret)
            initAuth()
        }
    }

    override suspend fun isValid(token: Token): Boolean {
        runCatching {
            retrofit<ITokenService>(connection = connection).checkTokenAsync(
                token.accessToken, auth()
            ).await().also { println(it) }
        }.onFailure {
            it.logPrintStackTrace("isValid(token: Token)")
        }.onSuccess {
            return true
        }
        return false
    }

    override fun saveAccessToken(token: Token?) {
        if (token != null) tokenDao.updateTokenData(token)
    }

    override suspend fun registerClient(secret: String): String {
        with(authListener()) {
            return runCatching {
                retrofit<IClientService>(connection = connection)
                    .registerClientAsync(secret)
                    .await().getObject<String>()
            }.onFailure {
                it.logPrintStackTrace("registerClient(secret: String)")
            }.onSuccess {
                onRegister()
            }.getOrDefault("")
        }
    }

    override suspend fun getAccessToken(): Token? {
        with(authListener()) {
            return runCatching {
                retrofit<ITokenService>(connection = connection)
                    .getTokenByClientAsync(
                        authentication = auth(), grant_type = "client_credentials"
                    ).await()
            }.onSuccess {
                onTokenCreated { (Log.i("TOKEN", "Token Created")) }
            }.getOrNull()
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

    private fun authListener(): IServerAuthListener {
        return this.authListener
    }
}