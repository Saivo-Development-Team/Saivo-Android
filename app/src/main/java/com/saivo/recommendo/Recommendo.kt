package com.saivo.recommendo

import android.app.Application
import com.jakewharton.threetenabp.AndroidThreeTen
import com.saivo.recommendo.data.Database
import com.saivo.recommendo.data.repository.client.ClientRepository
import com.saivo.recommendo.data.repository.client.IClientRepository
import com.saivo.recommendo.data.repository.token.ITokenRepository
import com.saivo.recommendo.data.repository.token.TokenRepository
import com.saivo.recommendo.data.repository.user.IUserRepository
import com.saivo.recommendo.data.repository.user.UserRepository
import com.saivo.recommendo.network.INetworkService
import com.saivo.recommendo.network.access.client.ClientDataSource
import com.saivo.recommendo.network.access.client.IClientDataSource
import com.saivo.recommendo.network.access.token.ITokenDataSource
import com.saivo.recommendo.network.access.token.TokenDataSource
import com.saivo.recommendo.network.access.user.IUserDataSource
import com.saivo.recommendo.network.access.user.UserDataSource
import com.saivo.recommendo.network.resquest.IClientService
import com.saivo.recommendo.network.resquest.ITokenService
import com.saivo.recommendo.network.resquest.IUserService
import com.saivo.recommendo.util.network.Connection
import com.saivo.recommendo.util.network.IConnection
import com.saivo.recommendo.view.viewable.auth.AuthViewModelFactory
import com.saivo.recommendo.view.viewable.user.UserViewModelFactory
import kotlinx.coroutines.InternalCoroutinesApi
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.androidXModule
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider
import org.kodein.di.generic.singleton

class Recommendo : Application(), KodeinAware {
    @InternalCoroutinesApi
    override val kodein = Kodein.lazy {
        import(androidXModule(this@Recommendo))

        bind() from singleton { Database(instance()) }
        bind() from singleton { instance<Database>().userDao() }
        bind() from singleton { instance<Database>().tokenDao() }
        bind() from singleton { instance<Database>().clientDao() }

        bind() from provider { AuthViewModelFactory() }

        bind<IConnection>() with singleton { Connection(instance()) }

        bind<IUserService>() with singleton { INetworkService.Companion<IUserService>(instance()) }
        bind<ITokenService>() with singleton { INetworkService.Companion<ITokenService>(instance()) }
        bind<IClientService>() with singleton { INetworkService.Companion<IClientService>(instance()) }

        bind<IUserDataSource>() with singleton { UserDataSource(instance()) }
        bind<ITokenDataSource>() with singleton { TokenDataSource(instance()) }
        bind<IClientDataSource>() with singleton { ClientDataSource(instance()) }

        bind<IUserRepository>() with singleton { UserRepository(instance(), instance()) }
        bind<ITokenRepository>() with singleton { TokenRepository(instance(), instance(), instance()) }
        bind<IClientRepository>() with singleton { ClientRepository(instance(), instance()) }

        bind() from provider { UserViewModelFactory(instance()) }
    }

    override fun onCreate() {
        AndroidThreeTen.init(this)
        super.onCreate()
    }
}