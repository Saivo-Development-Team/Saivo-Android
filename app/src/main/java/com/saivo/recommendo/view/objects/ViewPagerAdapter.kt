package com.saivo.recommendo.view.objects

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.saivo.recommendo.view.fragment.pages.ActivitiesFragment

class ViewPagerAdapter(fm: FragmentManager, behavior: Int) : FragmentPagerAdapter(fm, behavior) {
    private val totalItems = behavior

    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> ActivitiesFragment()
            1 -> ActivitiesFragment()
            else -> Fragment()
        }
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return when (position) {
            0 -> "New Activities"
            1 -> "Completed activities"
            else -> ""
        }
    }
    override fun getCount(): Int {
        return totalItems
    }
}