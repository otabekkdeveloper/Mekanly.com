package com.mekanly.presentation.ui.adapters.pagerAdapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.mekanly.presentation.ui.fragments.home.FragmentHome
import com.mekanly.ui.fragments.favorite.FavouriteFragment
import com.mekanly.ui.fragments.profile.FragmentProfile
import com.mekanly.ui.fragments.search.SearchFragment
import com.mekanly.presentation.ui.fragments.businessProfile.FragmentBusiness

class AdapterViewPager(fragmentActivity: FragmentActivity) : FragmentStateAdapter(fragmentActivity) {

    override fun getItemCount(): Int {
        return 5
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> FragmentHome()
            1 -> SearchFragment()
            2 -> FragmentBusiness()
            3 -> FavouriteFragment()
            4 -> FragmentProfile()
            else -> FragmentHome()
        }
    }
}
