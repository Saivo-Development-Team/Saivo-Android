package com.saivo.recommendo.view.objects.recommendations

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.saivo.recommendo.R
import com.saivo.recommendo.view.objects.RecyclerListAdapter
import kotlinx.android.synthetic.main.recommendation_card.view.*

class RecommendationViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val info = itemView.recommendation_info_text!!
    private val users = itemView.notification_users
    private val title = itemView.notification_title
    private val image = itemView.notification_image
    private val stars = itemView.notification_stars
    private val status = itemView.status
    private val rating = itemView.notification_rating
    private val details = itemView.details
    private val distance = itemView.distance
    private val category = itemView.recommendation_category

    companion object {
        operator fun invoke(root: ViewGroup): RecommendationViewHolder {
            return RecommendationViewHolder(
                LayoutInflater.from(root.context).inflate(
                    R.layout.recommendation_card, root, false
                )
            )
        }

        fun adapter(): RecyclerListAdapter<RecommendationCard, RecommendationViewHolder> {
            return RecyclerListAdapter(
                { invoke(it) }, { bind(it) },
                object : DiffUtil.ItemCallback<RecommendationCard>() {
                    override fun areItemsTheSame(
                        oldItem: RecommendationCard,
                        newItem: RecommendationCard
                    ): Boolean {
                        return oldItem == newItem
                    }

                    override fun areContentsTheSame(
                        oldItem: RecommendationCard,
                        newItem: RecommendationCard
                    ): Boolean {
                        return oldItem.hashCode() == newItem.hashCode()
                    }

                })
        }
    }

    fun bind(card: RecommendationCard) {
        info.text = card.info
        users.text = card.users.toString()
        title.text = card.title
        status.text = card.status
        rating.text = card.rating
        details.text = card.details
        stars.rating = card.stars.toFloat()
        distance.text = card.distance
        category.text = card.category

        val requestOptions = RequestOptions()
            .placeholder(R.drawable.background)
            .error(R.drawable.background)

        Glide.with(itemView.context)
            .applyDefaultRequestOptions(requestOptions)
            .load(card.image)
            .into(image)

    }
}