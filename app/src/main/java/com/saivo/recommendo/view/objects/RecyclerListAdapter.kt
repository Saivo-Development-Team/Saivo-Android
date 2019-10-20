package com.saivo.recommendo.view.objects

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder

class RecyclerListAdapter<T, VH : ViewHolder>(
    private val createHolder: (parent: ViewGroup) -> VH,
    private val bindHolder: VH.(item: T) -> Unit,
    diffUtilCallback: DiffUtil.ItemCallback<T>
) : ListAdapter<T, VH>(diffUtilCallback) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): VH {
        return createHolder(parent)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.bindHolder(getItem(position))
    }
}