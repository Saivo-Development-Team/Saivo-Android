package com.saivo.recommendo.view.objects.recommendations

import com.saivo.recommendo.view.objects.IRecyclerItem

data class RecCard(
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
): IRecyclerItem