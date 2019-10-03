package com.saivo.recommendo.view.objects.notifications

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.saivo.recommendo.R
import kotlinx.android.synthetic.main.notification_card.view.*
import kotlinx.android.synthetic.main.notification_card.view.notification_rating
import kotlinx.android.synthetic.main.notification_card.view.notification_title

class NotifCardViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
    private val rated = itemView.notification_rated
    private val title = itemView.notification_title
    private val rating = itemView.notification_rating
    private val stars = itemView.notification_stars
    private val users = itemView.notification_users
    private val image = itemView.notification_image

    companion object {
        operator fun invoke(root: ViewGroup): RecyclerView.ViewHolder {
            return NotifCardViewHolder(
                LayoutInflater.from(root.context).inflate(
                    R.layout.notification_card, root, false
                )
            )
        }
    }

    fun bind(card: NotifCard) {
        rated.text = card.rated
        title.text = card.title
        rating.text = card.rating
        stars.rating = card.stars.toFloat()
        users.text = card.users.toString()

        val requestOptions = RequestOptions()
            .placeholder(R.drawable.background)
            .error(R.drawable.background)

        Glide.with(itemView.context)
            .applyDefaultRequestOptions(requestOptions)
            .load(card.image)
            .into(image)
    }
}