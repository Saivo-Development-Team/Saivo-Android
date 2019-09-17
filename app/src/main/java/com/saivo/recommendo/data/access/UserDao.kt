package com.saivo.recommendo.data.access

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.saivo.recommendo.data.model.domain.User
import com.saivo.recommendo.data.model.infrastructure.UserData

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun updateUserData(user: User)

    @Query("select * from user where id = 0")
    fun getUserData(): LiveData<User>

    @Query("select user from user where id = 0")
    fun getUserId(): String

//    fun getUserRatings(): LiveData<List<Rating>>
}