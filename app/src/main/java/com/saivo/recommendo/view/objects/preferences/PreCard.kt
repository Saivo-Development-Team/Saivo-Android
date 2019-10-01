package com.saivo.recommendo.view.objects.preferences

import com.saivo.recommendo.view.objects.IRecyclerItem

class PreCard(
    val likes: String,
    val dislikes: String,
    val category: String,
    val description: String
): IRecyclerItem