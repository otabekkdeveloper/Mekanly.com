package com.mekanly.presentation.ui.adapters.pagerAdapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.mekanly.presentation.ui.fragments.profile.addHouse.subFragments.SubArchiveFragment
import com.mekanly.presentation.ui.fragments.profile.addHouse.subFragments.SubSearchingHousesFragment
import com.mekanly.presentation.ui.fragments.profile.addHouse.subFragments.SubNotificationsFragment

class NotificationViewPager (fragmentActivity: FragmentActivity) : FragmentStateAdapter(fragmentActivity) {

    override fun getItemCount(): Int {
        return 3
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> SubNotificationsFragment()
            1 -> SubSearchingHousesFragment()
            2 -> SubArchiveFragment()
            else -> SubNotificationsFragment()
        }
    }
}