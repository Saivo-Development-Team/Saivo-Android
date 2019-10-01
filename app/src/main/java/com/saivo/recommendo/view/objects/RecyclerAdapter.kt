package com.saivo.recommendo.view.objects

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.saivo.recommendo.view.objects.preferences.PreCard
import com.saivo.recommendo.view.objects.preferences.PreCardViewHolder
import com.saivo.recommendo.view.objects.recommendations.RecCard
import com.saivo.recommendo.view.objects.recommendations.RecCardViewHolder

class RecyclerAdapter<T : IRecyclerItem>(private val clazz: Class<T>) :
    RecyclerView.Adapter<ViewHolder>() {

    private var items = ArrayList<T>()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        return when {
            checkType<RecCard>() -> RecCardViewHolder.invoke(parent)
            checkType<PreCard>() -> PreCardViewHolder.invoke(parent)
            else -> throw Exception("${clazz.name} is not a valid RecyclerItem")
        }
    }

    fun addToListItems(items: ArrayList<T>) {
        this.items = items
    }

    private inline fun <reified I : IRecyclerItem> checkType(): Boolean {
        return clazz.isAssignableFrom(I::class.java)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        when (holder) {
            is RecCardViewHolder -> return holder.bind(items[position] as RecCard)
            is PreCardViewHolder -> return holder.bind(items[position] as PreCard)
        }
    }
}