package com.saivo.recommendo.view.fragment


import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import com.saivo.recommendo.R
import com.saivo.recommendo.data.access.ClientDao
import com.saivo.recommendo.data.access.TokenDao
import com.saivo.recommendo.data.model.infrastructure.Client
import com.saivo.recommendo.data.model.infrastructure.Token
import com.saivo.recommendo.network.resquest.IClientService
import com.saivo.recommendo.network.resquest.ITokenService
import com.saivo.recommendo.util.helpers.basicAuth
import com.saivo.recommendo.util.helpers.createUUID
import com.saivo.recommendo.util.helpers.retrofit
import com.saivo.recommendo.util.network.IClient
import com.saivo.recommendo.util.network.IConnection
import com.saivo.recommendo.util.network.IToken
import com.saivo.recommendo.view.viewmodel.auth.IServerAuthListener
import kotlinx.android.synthetic.main.fragment_splash.*
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein
import org.kodein.di.generic.instance


@ExperimentalStdlibApi
class SplashFragment : CoroutineFragment(), KodeinAware, IServerAuthListener {


    override val kodein: Kodein by closestKodein()
//    private lateinit var serverViewModel: IServerAuth
//    private val viewModelFactory: ViewModelFactory by instance()

    private val secret = createUUID()
    private val tokenDao: TokenDao by instance()
    private val clientDao: ClientDao by instance()
    private val connection: IConnection by instance()
    private val token = IToken
    private val client = IClient


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_splash, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        serverViewModel = ViewModelProvider(this, viewModelFactory).get(ServerViewModel::class.java)
//        serverViewModel.addServerAuthListener(this)
//        launch { serverViewModel.authInit() }

        initClientWithToken()
            .invokeOnCompletion {
                Navigation.findNavController(view).navigate(R.id.to_login_action)
            }
    }

    override fun onInit() {
        setLoadingText("Getting Access...")
    }

    override fun onRegister() {
        setLoadingText("Registering...")
    }

    override fun onRegistered() {
        setLoadingText("Got Access")
    }

    override fun onCreateToken(block: () -> Any) {
        setLoadingText("All Done!")
    }

    override fun onTokenCreated(block: ((token: Token) -> Any) -> Unit) {
        setLoadingText("All Done!")
    }


    private fun setLoadingText(text: String) {
        launch(this.coroutineContext) {
            splash_loading_text.text = text
        }
    }

    private fun initClientWithToken() = launch(IO) {
        val client = clientDao.getClient()
        runCatching {

            return@runCatching getToken(secret)
        }.onFailure {
            Log.e("AuthFailure", it.message.toString())
        }.onSuccess {
            runCatching {
                checkToken(it, client)
            }.onFailure {
                recreateToken(client)
            }
        }
    }

    private suspend fun recreateToken(client: Client?) {
        runCatching {
            if (client != null) createAccessToken(tokenDao, client)
        }.onFailure {
            when (it) {
                is retrofit2.HttpException -> {
                    registerClient(secret)
//                    recreateToken(client)
                }
            }
        }
    }

    private suspend fun getToken(secret: String): String {
        return token(tokenDao, client(clientDao) { clientDao ->
            Client(clientSecret = secret, clientId = registerClient(secret)).also {
                clientDao.updateClient(it.apply { clientSecret = it.clientSecret })
            }
        }) { tokenDao, client -> createAccessToken(tokenDao, client) }
    }

    private suspend fun checkToken(
        token: String,
        client: Client?
    ): Any = withContext(IO) {
        setLoadingText("Checking Access")
        return@withContext when {
            client != null -> retrofit<ITokenService>(connection = connection).checkTokenAsync(
                token,
                basicAuth(client.clientId, client.clientSecret)
            ).await()
            else -> Any()
        }
    }

    private suspend fun createAccessToken(tokenDao: TokenDao, client: Client): String =
        withContext(IO) {
            setLoadingText("Getting Access")
            retrofit<ITokenService>(connection = connection).getTokenByClientAsync(
                authentication = basicAuth(client.clientId, client.clientSecret),
                grant_type = "client_credentials"
            ).await().apply {
                tokenDao.updateTokenData(this)
            }.accessToken
        }

    private suspend fun registerClient(secret: String): String = withContext(IO) {
        setLoadingText("Registering Device")
        retrofit<IClientService>(connection = connection)
            .registerClientAsync(secret)
            .await().data as String
    }

}
