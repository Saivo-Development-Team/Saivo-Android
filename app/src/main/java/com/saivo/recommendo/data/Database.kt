package com.saivo.recommendo.data

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.saivo.recommendo.data.access.ClientDao
import com.saivo.recommendo.data.access.PreferenceDao
import com.saivo.recommendo.data.access.TokenDao
import com.saivo.recommendo.data.access.UserDao
import com.saivo.recommendo.data.model.domain.Preference
import com.saivo.recommendo.data.model.domain.Rating
import com.saivo.recommendo.data.model.domain.Recommendation
import com.saivo.recommendo.data.model.domain.User
import com.saivo.recommendo.data.model.infrastructure.Client
import com.saivo.recommendo.data.model.infrastructure.Token
import com.saivo.recommendo.util.helpers.Converters
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.internal.synchronized
import androidx.room.Database as RDatabase

@RDatabase(
    entities =
    [
        User::class, Rating::class, Recommendation::class,
        Preference::class, Client::class, Token::class
    ],
    version = 15
)
@TypeConverters(Converters::class)
abstract class Database : RoomDatabase() {

    companion object {
        @Volatile
        private var instance: Database? = null
        private val lock = Any()

        @InternalCoroutinesApi
        operator fun invoke(context: Context) = instance ?: synchronized(lock) {
            instance ?: database(context).also { instance = it }
        }

        private fun database(context: Context) = Room.databaseBuilder(
            context.applicationContext, Database::class.java,
            "recommendo.sqlite"
        ).fallbackToDestructiveMigration()
            .build()
    }

    abstract fun userDao(): UserDao
    abstract fun tokenDao(): TokenDao
    abstract fun clientDao(): ClientDao
    abstract fun preferenceDao(): PreferenceDao
}