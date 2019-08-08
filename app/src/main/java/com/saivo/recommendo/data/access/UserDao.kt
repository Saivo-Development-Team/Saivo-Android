package com.saivo.recommendo.data.access

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.saivo.recommendo.data.model.domain.Rating
import com.saivo.recommendo.data.model.domain.User

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addOrReplaceUser(user: User)

    @Query("select * from user where id = :id")
    fun getUser(id : String): LiveData<User>

    fun getUserRatings(): LiveData<List<Rating>>
}