package com.saivo.recommendo.data.model.infrastructure


import androidx.room.Entity
import androidx.room.PrimaryKey
import com.saivo.recommendo.util.helpers.CLIENT_ID

@Entity(tableName = "client")
data class Client(
    @PrimaryKey(autoGenerate = false)
    val deviceClient: Int = CLIENT_ID,
    var clientId: String = "",
    var clientSecret: String
)