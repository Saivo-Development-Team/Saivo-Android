package com.saivo.recommendo.util.helpers

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.saivo.recommendo.data.model.domain.Preference

class Converters {
    @TypeConverter
    fun preferenceToList(json: String): List<Preference> {
        val gson = Gson()
        val type = object : TypeToken<List<Preference>>() {}.type
        return gson.fromJson(json, type)
    }

    @TypeConverter
    fun preferenceToString(list: List<Preference>): String {
        val gson = Gson()
        val type = object : TypeToken<List<Preference>>() {}.type
        return gson.toJson(list, type)
    }
}