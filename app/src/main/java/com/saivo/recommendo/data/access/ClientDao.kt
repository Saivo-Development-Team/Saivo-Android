package com.saivo.recommendo.data.access

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.saivo.recommendo.data.model.infrastructure.Client

@Dao
interface ClientDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun updateClientCredentials(client: Client)

    @Query("select * from client where deviceClient = 0")
    fun getClientCredentials(): Client

}