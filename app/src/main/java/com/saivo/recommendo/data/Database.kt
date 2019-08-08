package com.saivo.recommendo.data

import android.content.Context
import androidx.room.Room
import androidx.room.Database as RDatabase
import androidx.room.RoomDatabase
import com.saivo.recommendo.data.access.ClientDao
import com.saivo.recommendo.data.access.UserDao
import com.saivo.recommendo.data.model.domain.User
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.internal.synchronized

@RDatabase(
    entities = [User::class],
    version = 1
)
abstract class Database : RoomDatabase() {

    companion object{
        @Volatile private var instance: Database? = null
        private val lock = Any()

        @InternalCoroutinesApi
        operator fun invoke(context: Context) = instance ?: synchronized(
            lock
        ) {
            instance ?: build(
                context
            ).also { instance = it }
        }

        private fun build(context: Context) = Room.databaseBuilder(
            context.applicationContext, Database::class.java,
            "recommendo.sqlite").build()
    }

    abstract fun userDao() : UserDao
    abstract fun clientDap() : ClientDao
}