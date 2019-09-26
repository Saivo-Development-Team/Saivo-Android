package com.saivo.recommendo.view.fragment.pages


import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.saivo.recommendo.R
import com.saivo.recommendo.util.helpers.toastMessage
import com.saivo.recommendo.view.objects.recommendations.RecCard
import com.saivo.recommendo.view.objects.RecyclerAdapter
import kotlinx.android.synthetic.main.fragment_recommendations.*

/**
 * A simple [Fragment] subclass.
 */
class RecommendationsFragment : Fragment() {

    private val recyclerAdapter = RecyclerAdapter(RecCard::class.java)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_recommendations, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        recommendations_recycler_view.apply {
            layoutManager = LinearLayoutManager(this@RecommendationsFragment.context)
            adapter = recyclerAdapter
        }
        recyclerAdapter.addToListItems(getData())
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

    private fun getData(): ArrayList<RecCard> {
        val data = arrayListOf<RecCard>()
        data.add(
            RecCard(
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
            RecCard(
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
