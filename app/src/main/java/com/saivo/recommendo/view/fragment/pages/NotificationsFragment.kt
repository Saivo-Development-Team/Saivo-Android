package com.saivo.recommendo.view.fragment.pages


import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.saivo.recommendo.R
import com.saivo.recommendo.view.objects.notifications.NotificationCard
import com.saivo.recommendo.view.objects.notifications.NotificationViewHolder
import kotlinx.android.synthetic.main.fragment_dashboard.*
import kotlinx.android.synthetic.main.fragment_notifications.*

/**
 * A simple [Fragment] subclass.
 */
class NotificationsFragment : Fragment() {

    private val recyclerAdapter = NotificationViewHolder.adapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        return inflater.inflate(R.layout.fragment_notifications, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        notifications_recycler_view.apply {
            layoutManager = LinearLayoutManager(this@NotificationsFragment.context)
            adapter = recyclerAdapter
            recyclerAdapter.submitList(getData())
        }
        setupMaterialToolbar()
    }

    private fun setupMaterialToolbar() {
        (activity as AppCompatActivity).apply {
            setSupportActionBar(materialToolbar)
            title = findNavController().currentDestination?.label
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.search_menu, menu)
    }

    private fun getData(): ArrayList<NotificationCard> {
        val data = arrayListOf<NotificationCard>()
        data.add(
            NotificationCard(
                rated = "Recommended based off park rating",
                title = "Claremont Park",
                rating = "4,7",
                stars = 4.7,
                users = 55,
                image = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRBC7oKR9Y15uxvQjrkYNnFKFNaa-Rv_nSbQGfU5X3UrzOWirF2"
            )

        )
        data.add(
            NotificationCard(
                rated = "Recommended based off restaurant rating",
                title = "Tiger's Milk",
                rating = "4,1",
                stars = 4.1,
                users = 112,
                image = "https://tigersmilk.co.za/wp-content/uploads/2018/03/001-2.jpg"
            )

        )
        data.add(
            NotificationCard(
                rated = "Recommended based off monument rating",
                title = "Rhodes Memorial",
                rating = "4,0",
                stars = 4.0,
                users = 150,
                image = "https://media-cdn.tripadvisor.com/media/photo-s/0d/a3/90/71/view-from-rhodes-memorial.jpg"
            )

        )
        return data
    }
}
