package com.mekanly.presentation.ui.adapters.pagerAdapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.mekanly.presentation.ui.fragments.addHouse.subFragments.SubArchiveFragment
import com.mekanly.presentation.ui.fragments.addHouse.subFragments.SubLookingHousesFragment
import com.mekanly.presentation.ui.fragments.addHouse.subFragments.SubNotificationsFragment
import com.mekanly.presentation.ui.fragments.businessProfile.SearchBusinessNotificationsFragment

class BusinessSearchViewPager (fragmentActivity: FragmentActivity) : FragmentStateAdapter(fragmentActivity) {

    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> SearchBusinessNotificationsFragment()
            1 -> SearchBusinessNotificationsFragment()
            else -> SubNotificationsFragment()
        }
    }
}