package com.saivo.recommendo.view.objects.preferences

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.textview.MaterialTextView
import com.saivo.recommendo.R
import com.saivo.recommendo.view.objects.RecyclerListAdapter
import kotlinx.android.synthetic.main.preference_card.view.*

class PreferenceViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val likeText = itemView.preference_like_text
    private val dislikeText = itemView.preference_dislike_text
    private val categoryText: MaterialTextView = itemView.preference_category_text
    private val descriptionText: MaterialTextView = itemView.preference_description_text

    companion object {
        operator fun invoke(root: ViewGroup): PreferenceViewHolder {
            return PreferenceViewHolder(
                LayoutInflater.from(root.context).inflate(
                    R.layout.preference_card, root, false
                )
            )
        }

        fun adapter(): RecyclerListAdapter<PreferenceCard, PreferenceViewHolder> {
            return RecyclerListAdapter(
                { invoke(it) }, { bind(it) },
                object : DiffUtil.ItemCallback<PreferenceCard>() {
                    override fun areItemsTheSame(oldItem: PreferenceCard, newItem: PreferenceCard): Boolean {
                        return oldItem == newItem
                    }

                    override fun areContentsTheSame(oldItem: PreferenceCard, newItem: PreferenceCard): Boolean {
                        return oldItem.hashCode() == newItem.hashCode()
                    }

                })
        }
    }

    fun bind(card: PreferenceCard) {
        likeText.text = card.likes
        dislikeText.text = card.dislikes
        categoryText.text = card.category
        descriptionText.text = card.description
    }
}