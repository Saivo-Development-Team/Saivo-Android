package com.saivo.recommendo.view.`object`

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.saivo.recommendo.R

class RecyclerAdapter<T> : RecyclerView.Adapter<ViewHolder>() {

    private var items = ArrayList<T>()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        return RecoCardViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.recommendation_card,
                parent,
                false
            )
        )
    }

    fun addToListItems(items: ArrayList<T>) {
        this.items = items
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        when(holder) {
            is RecoCardViewHolder -> return holder.bind(items[position] as RecoCard)
        }
    }

}