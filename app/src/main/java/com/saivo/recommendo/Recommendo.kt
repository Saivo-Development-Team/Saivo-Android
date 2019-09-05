package com.saivo.recommendo

import android.app.Application
import com.jakewharton.threetenabp.AndroidThreeTen
import com.saivo.recommendo.data.Database
import com.saivo.recommendo.data.repository.token.ITokenRepository
import com.saivo.recommendo.data.repository.token.TokenRepository
import com.saivo.recommendo.data.repository.user.IUserRepository
import com.saivo.recommendo.data.repository.user.UserRepository
import com.saivo.recommendo.network.INetworkService
import com.saivo.recommendo.network.access.user.IUserDataSource
import com.saivo.recommendo.network.access.user.UserDataSource
import com.saivo.recommendo.view.viewable.user.UserViewModelFactory
import com.saivo.recommendo.util.network.Connection
import com.saivo.recommendo.util.network.IConnectivity
import com.saivo.recommendo.view.viewable.auth.AuthViewModelFactory
import kotlinx.coroutines.InternalCoroutinesApi
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.androidXModule
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider
import org.kodein.di.generic.singleton
import retrofit2.Retrofit

class Recommendo : Application(), KodeinAware {
    @InternalCoroutinesApi
    override val kodein = Kodein.lazy {
        import(androidXModule(this@Recommendo))

        bind() from singleton { Database(instance()) }
        bind() from singleton { instance<Database>().userDao() }
        bind() from singleton { instance<Database>().tokenDao() }
        bind() from singleton { instance<Database>().clientDao() }
        bind<IConnectivity>() with singleton { Connection(instance()) }
        bind<Retrofit>() with singleton { INetworkService.Companion(instance(), instance()) }
        bind<ITokenRepository>() with singleton { TokenRepository(instance()) }
        bind<IUserDataSource>() with singleton {
            UserDataSource(
                instance()
            )
        }
        bind<IUserRepository>() with singleton { UserRepository(instance(), instance()) }
        bind() from provider { AuthViewModelFactory() }
        bind() from provider { UserViewModelFactory(instance()) }
    }

    override fun onCreate() {
        AndroidThreeTen.init(this)
        super.onCreate()
    }
}