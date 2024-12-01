package com.mekanly.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.mekanly.FlowFragment
import com.mekanly.ProfilePage.BildirishlerProfileFragment
import com.mekanly.ProfilePage.MaglumatProfileFragment
import com.mekanly.ProfilePage.SuratlarProfileFragment
import com.mekanly.search.EmlakGozleyanlerFragment
import com.mekanly.search.EmlaklerFragment

class ProfileTabLayoutAdapter (fragmentActivity: FragmentActivity) : FragmentStateAdapter(fragmentActivity) {

    override fun getItemCount(): Int {
        return 3 // Количество вкладок
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> BildirishlerProfileFragment()
            1 -> SuratlarProfileFragment()
            2 -> MaglumatProfileFragment()

            else -> FlowFragment()
        }
    }
}