package com.mekanly.adapters.pagerAdapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.mekanly.FlowFragment
import com.mekanly.search.EmlakGozleyanlerFragment
import com.mekanly.search.EmlaklerFragment

class SearchViewAdapter (fragmentActivity: FragmentActivity) : FragmentStateAdapter(fragmentActivity) {

    override fun getItemCount(): Int {
        return 2 // Количество вкладок
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> EmlaklerFragment()
            1 -> EmlakGozleyanlerFragment()

            else -> FlowFragment()
        }
    }
}