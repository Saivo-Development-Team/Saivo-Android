package com.saivo.recommendo.data.access

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.saivo.recommendo.data.model.infrastructure.Token
import com.saivo.recommendo.util.helpers.TOKEN_ID

@Dao
interface TokenDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun updateTokenData(token: Token)

    @Query("select accessToken from token where deviceToken = $TOKEN_ID")
    fun getAccessToken(): String?

    @Query("select tokenType from token where deviceToken = $TOKEN_ID")
    fun getTokenType(): String?

    @Query("select * from token where deviceToken = $TOKEN_ID")
    fun getToken(): Token?

}