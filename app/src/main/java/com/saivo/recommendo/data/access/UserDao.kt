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

    @Query("select * from user where email = :email")
    fun getUserData(email: String): LiveData<User>

    @Query("select id from user where email = :email")
    fun getUserId(email: String): String
//    fun getUserRatings(): LiveData<List<Rating>>
}