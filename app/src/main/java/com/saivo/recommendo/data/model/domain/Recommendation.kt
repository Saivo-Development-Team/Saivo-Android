package com.saivo.recommendo.data.model.domain

import androidx.room.Embedded

data class Recommendation(
    val id: String = "",

    @Embedded(prefix = "activity_")
    val activity: Activity
)