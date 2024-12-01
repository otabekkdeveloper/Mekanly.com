package com.mekanly.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.mekanly.FlowFragment
import com.mekanly.FourthFragment
import com.mekanly.ProfilePage.ProfileFragment
import com.mekanly.SearchFragment
import com.mekanly.SatyjylarFragment

class ViewPagerAdapter(fragmentActivity: FragmentActivity) : FragmentStateAdapter(fragmentActivity) {

    override fun getItemCount(): Int {
        return 5
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> FlowFragment()
            1 -> SearchFragment()
            2 -> SatyjylarFragment()
            3 -> FourthFragment()
            4 -> ProfileFragment()
            else -> FlowFragment()
        }
    }
}
