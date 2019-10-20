package com.saivo.recommendo.view.fragment.pages


import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.saivo.recommendo.R
import com.saivo.recommendo.view.objects.recommendations.RecommendationCard
import com.saivo.recommendo.view.objects.recommendations.RecommendationViewHolder
import kotlinx.android.synthetic.main.fragment_activities.*

/**
 * A simple [Fragment] subclass.
 */
class ActivitiesFragment : Fragment() {

    private val recyclerAdapter = RecommendationViewHolder.adapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_activities, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        activities_recycler_view.apply {
            layoutManager = LinearLayoutManager(this@ActivitiesFragment.context)
            adapter = recyclerAdapter
        }
        recyclerAdapter.submitList(getData())
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        super.onPrepareOptionsMenu(menu)
        (menu.findItem(R.id.toolbar_search).actionView as SearchView)
            .setOnQueryTextListener(object :
                SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(p0: String?): Boolean {
                    return true
                }

                override fun onQueryTextChange(p0: String?): Boolean {
                    return true
                }
            })
    }

    private fun getData(): ArrayList<RecommendationCard> {
        val data = arrayListOf<RecommendationCard>()
        data.add(
            RecommendationCard(
                rating = "4,5",
                image = "https://gcs.thesouthafrican.com/2019/08/91fab20a-worlds-friendliest-cities-cape-town-1200x858.jpg",
                stars = 4.5,
                users = 34,
                category = "City",
                status = "Close",
                distance = "56km",
                details = "Capital of South Africa",
                info = "RR",
                title = "Cape Town has been named Africa's leading digital city"
            )
        )
        data.add(
            RecommendationCard(
                rating = "3,2",
                image = "https://cdn.britannica.com/49/100349-050-24E63356/view-central-business-district-Johannesburg-South-Africa.jpg",
                stars = 3.2,
                users = 50,
                category = "City",
                status = "Far",
                distance = "1245km",
                details = "Johannesburg, South Africa's biggest city",
                info = "ER",
                title = "11 Things you have to do when you visit Johannesburg"
            )
        )
        return data
    }

}
