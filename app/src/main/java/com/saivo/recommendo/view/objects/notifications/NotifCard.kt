package com.saivo.recommendo.view.objects.notifications

import com.saivo.recommendo.view.objects.IRecyclerItem

data class NotifCard (
    val rated: String,
    val title: String,
    val rating: String,
    val stars: Double,
    val users: Int,
    val image: String
): IRecyclerItem
