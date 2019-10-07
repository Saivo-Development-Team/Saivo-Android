package com.saivo.recommendo.data.access

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.saivo.recommendo.data.model.infrastructure.Client
import com.saivo.recommendo.util.helpers.CLIENT_ID
import kotlinx.coroutines.Deferred

@Dao
interface ClientDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun updateClient(client: Client)

    @Query("select * from client where deviceClient = $CLIENT_ID")
    fun getClient(): Client?

}