package com.saivo.recommendo.view.objects.recommendations

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.saivo.recommendo.R
import kotlinx.android.synthetic.main.recommendation_card.view.*

class RecCardViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val info = itemView.info!!
    private val users = itemView.users
    private val title = itemView.title
    private val image = itemView.image
    private val stars = itemView.stars
    private val status = itemView.status
    private val rating = itemView.rating
    private val details = itemView.details
    private val distance = itemView.distance
    private val category = itemView.category

    companion object {
        operator fun invoke(root: ViewGroup): RecyclerView.ViewHolder {
            return RecCardViewHolder(
                LayoutInflater.from(root.context).inflate(
                    R.layout.recommendation_card, root, false
                )
            )
        }
    }

    fun bind(card: RecCard) {
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