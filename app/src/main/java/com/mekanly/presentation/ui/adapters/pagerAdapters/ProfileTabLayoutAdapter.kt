package com.mekanly.presentation.ui.adapters.pagerAdapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.mekanly.presentation.ui.fragments.flow.FragmentHome

class ProfileTabLayoutAdapter (fragmentActivity: FragmentActivity) : FragmentStateAdapter(fragmentActivity) {

    override fun getItemCount(): Int {
        return 0// Количество вкладок
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            else -> FragmentHome()
        }
    }
}