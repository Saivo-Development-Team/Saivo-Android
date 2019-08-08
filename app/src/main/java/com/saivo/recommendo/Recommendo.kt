package com.saivo.recommendo

import android.app.Application
import com.jakewharton.threetenabp.AndroidThreeTen
import com.saivo.recommendo.data.Database
import com.saivo.recommendo.network.Service
import com.saivo.recommendo.util.network.Connection
import com.saivo.recommendo.util.network.Connectivity
import kotlinx.coroutines.InternalCoroutinesApi
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.androidXModule
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.singleton

class Recommendo : Application(), KodeinAware {
    @InternalCoroutinesApi
    override val kodein = Kodein.lazy {
        import(androidXModule(this@Recommendo))

        bind() from singleton { Database(instance()) }
        bind() from singleton { Service(instance()) }
        bind() from singleton { instance<Database>().userDao() }
        bind() from singleton { instance<Database>().clientDap() }

        bind<Connectivity>() with singleton { Connection(instance()) }
    }

    override fun onCreate() {
        super.onCreate()
        AndroidThreeTen.init(this)
    }
}