package com.saivo.recommendo.data.access

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.saivo.recommendo.data.model.domain.Preference

@Dao
interface PreferenceDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun savePreference(preference: Preference)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun savePreferences(preferences: List<Preference>)

    @Query("select * from user_preference where id = :preference_id")
    fun getPreference(preference_id: String): Preference?

    @Query("select * from user_preference")
    fun getPreferences(): List<Preference>
}