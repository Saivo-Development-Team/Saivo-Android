package com.saivo.recommendo.view.main.page

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.saivo.recommendo.R
import kotlinx.android.synthetic.main.recyclerview_item.view.*

class RecyclerViewAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>(){

    private var items: List<RecyclerView> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
    return RecyclerViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.recyclerview_item,parent, false)
    )

    }




    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
    when(holder){
        is RecyclerViewHolder -> {
            holder.bind(items.get(position))
        }
    }

    }


    override fun getItemCount(): Int {
        return items.size
    }

    fun submitList(RecyclerList: List<RecyclerView>)
    {items = RecyclerList
    }

    class RecyclerViewHolder constructor(
       itemView: View
    ): RecyclerView.ViewHolder(itemView){
        val preference : TextView = itemView.preference

        fun bind(recyclerView: RecyclerView){
            preference.text.equals( recyclerView.preference)


        }
    }

}

