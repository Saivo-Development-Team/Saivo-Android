package com.saivo.recommendo.data.model.infrastructure


import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "client")
data class Client(
    @PrimaryKey(autoGenerate = false)
    val deviceClient: Int = 0,
    var clientId: String = "",
    var clientSecret: String
)