package com.saivo.recommendo.view.fragment.pages


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager

import com.saivo.recommendo.R
import com.saivo.recommendo.view.objects.RecyclerAdapter
import com.saivo.recommendo.view.objects.notifications.NotifCard
import kotlinx.android.synthetic.main.fragment_notifications.*

/**
 * A simple [Fragment] subclass.
 */
class NotificationsFragment : Fragment() {

    private val recyclerAdapter = RecyclerAdapter(NotifCard::class.java)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_notifications, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        notifications_recycler_view.apply {
            layoutManager = LinearLayoutManager(this@NotificationsFragment.context)
            adapter = recyclerAdapter
        }

        recyclerAdapter.addToListItems(getData())
    }

    private fun getData(): ArrayList<NotifCard> {
        val data = arrayListOf<NotifCard>()
        data.add(
            NotifCard(
                rated = "Recommended based off park rating",
                title = "Claremont Park",
                rating ="4,7" ,
                stars = 4.7,
                users = 55,
                image = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRBC7oKR9Y15uxvQjrkYNnFKFNaa-Rv_nSbQGfU5X3UrzOWirF2"
            )

        )
        return data
    }
}
