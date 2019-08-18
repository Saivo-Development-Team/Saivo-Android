package com.saivo.recommendo

import android.app.Application
import com.jakewharton.threetenabp.AndroidThreeTen
import com.saivo.recommendo.data.Database
import com.saivo.recommendo.data.repository.UserRepository
import com.saivo.recommendo.data.repository.UserRepositoryImpl
import com.saivo.recommendo.network.NetworkService
import com.saivo.recommendo.network.access.DataSource
import com.saivo.recommendo.network.access.NetworkDataSource
import com.saivo.recommendo.view.viewmodel.UserViewModelFactory
import com.saivo.recommendo.util.network.Connection
import com.saivo.recommendo.util.network.Connectivity
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
        bind() from singleton { instance<Database>().clientDao() }
        bind<Connectivity>() with singleton { Connection(instance()) }
        bind() from singleton { NetworkService(instance()) }
        bind<DataSource>() with singleton { NetworkDataSource(instance()) }
        bind<UserRepository>() with singleton { UserRepositoryImpl(instance(), instance()) }
        bind() from provider { UserViewModelFactory(instance()) }
    }

    override fun onCreate() {
        super.onCreate()
        AndroidThreeTen.init(this)
    }
}