package com.saivo.recommendo.view.main.page

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.saivo.recommendo.R
import kotlinx.android.synthetic.main.recyclerview_item.view.*

class RecyclerViewAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>(){

    private var items = ArrayList<RecyclerItem>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
    return RecyclerViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.recyclerview_item,parent, false)
    )

    }




    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
    when(holder){
        is RecyclerViewHolder -> {
            holder.bind(items[position])
        }
    }

    }


    override fun getItemCount(): Int {
        return items.size
    }

    fun submitList(list: ArrayList<RecyclerItem>)
    {items = list
    }

    class RecyclerViewHolder constructor(
       itemView: View
    ): RecyclerView.ViewHolder(itemView){
        private val preference : TextView = itemView.preference

        fun bind(recyclerItem: RecyclerItem){
            preference.text = recyclerItem.preference


        }
    }

}

