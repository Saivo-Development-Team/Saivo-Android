package com.saivo.recommendo.data.model.infrastructure

import com.saivo.recommendo.data.model.domain.Rating

data class Activity(
    val id: String,
    val title: String,
    val ratings: List<Rating>,
    val category: String,
    val location: String,
    val description: String
)