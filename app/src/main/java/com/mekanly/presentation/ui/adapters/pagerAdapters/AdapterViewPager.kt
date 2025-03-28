package com.mekanly.presentation.ui.adapters.pagerAdapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.mekanly.presentation.ui.fragments.home.FragmentHome
import com.mekanly.FourthFragment
import com.mekanly.presentation.ui.fragments.profile.ProfileFragment
import com.mekanly.presentation.ui.fragments.search.SearchFragment
import com.mekanly.presentation.ui.business.FragmentBusiness

class AdapterViewPager(fragmentActivity: FragmentActivity) : FragmentStateAdapter(fragmentActivity) {

    override fun getItemCount(): Int {
        return 5
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> FragmentHome()
            1 -> SearchFragment()
            2 -> FragmentBusiness()
            3 -> FourthFragment()
            4 -> ProfileFragment()
            else -> FragmentHome()
        }
    }
}
