package com.saivo.recommendo.view.fragment.pages


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayout
import com.saivo.recommendo.R
import com.saivo.recommendo.view.objects.ViewPagerAdapter
import kotlinx.android.synthetic.main.fragment_recommendations.*

/**
 * A simple [Fragment] subclass.
 */
class RecommendationsFragment : Fragment() {

    private lateinit var viewPagerAdapter: ViewPagerAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewPagerAdapter = ViewPagerAdapter(this.parentFragmentManager, 2)
        return inflater.inflate(R.layout.fragment_recommendations, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        RecommendationViewPager.adapter = viewPagerAdapter
        RecommendationTabLayout.setupWithViewPager(RecommendationViewPager)
        RecommendationTabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabReselected(tab: TabLayout.Tab?) {}

            override fun onTabUnselected(tab: TabLayout.Tab?) {}

            override fun onTabSelected(tab: TabLayout.Tab?) {
                tab?.position?.let { RecommendationViewPager.currentItem = it }
            }
        })
    }
}
