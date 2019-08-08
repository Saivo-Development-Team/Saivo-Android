package com.saivo.recommendo.data.access

import androidx.lifecycle.LiveData
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.saivo.recommendo.data.model.domain.Rating
import com.saivo.recommendo.data.model.domain.User
import com.saivo.recommendo.data.model.infrastructure.Client

interface ClientDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addOrReplace(client: Client)

    @Query("select * from client where clientId = :id")
    fun getUser(id : String): LiveData<User>

    fun getUserRatings(): LiveData<List<Rating>>

}