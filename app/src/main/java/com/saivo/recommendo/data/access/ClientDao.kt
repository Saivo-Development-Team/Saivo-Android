package com.saivo.recommendo.data.access

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.saivo.recommendo.data.model.infrastructure.AccessToken
import com.saivo.recommendo.data.model.infrastructure.Client

@Dao
interface ClientDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addOrReplace(client: Client)

    @Query("select * from client where clientId = :clientId")
    fun getClient(clientId : String): LiveData<Client>
//
//    fun getClientToken(): LiveData<AccessToken>

}