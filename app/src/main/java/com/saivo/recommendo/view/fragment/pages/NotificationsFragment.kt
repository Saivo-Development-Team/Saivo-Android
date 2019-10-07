package com.saivo.recommendo.view.fragment.pages


import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager

import com.saivo.recommendo.R
import com.saivo.recommendo.network.access.notification.INotificationDataSource
import com.saivo.recommendo.view.fragment.CoroutineFragment
import com.saivo.recommendo.view.objects.RecyclerAdapter
import com.saivo.recommendo.view.objects.notifications.NotifCard
import kotlinx.android.synthetic.main.fragment_dashboard.*
import kotlinx.android.synthetic.main.fragment_notifications.*
import kotlinx.coroutines.launch
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein
import org.kodein.di.generic.instance

/**
 * A simple [Fragment] subclass.
 */
class NotificationsFragment : CoroutineFragment(), KodeinAware {
    override val kodein by closestKodein()

    private val notificationDataSource: INotificationDataSource by instance()
    private val recyclerAdapter = RecyclerAdapter(NotifCard::class.java)

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
            recyclerAdapter.addToListItems(getData())

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

    private fun getData(): ArrayList<NotifCard> {


        val data = arrayListOf<NotifCard>()
        launch {
            notificationDataSource.getNotificationForUserAsync("Du").forEach {
                it.apply {
                    data.add(
                        NotifCard(
                            rated = rated,
                            title = title,
                            rating =rating,
                            stars = stars,
                            users = users,
                            image = image
                        )
                    )
                }

            }
        }
        return data
    }
}
