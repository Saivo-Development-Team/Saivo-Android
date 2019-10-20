package com.saivo.recommendo.view.objects.recommendations

data class RecommendationCard(
    val info: String,
    val users: Int,
    val title: String,
    val image: String,
    val stars: Double,
    val status: String,
    val rating: String,
    val details: String,
    val distance: String,
    val category: String
)