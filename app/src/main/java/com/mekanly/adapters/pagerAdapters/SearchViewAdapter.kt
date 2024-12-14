package com.mekanly.adapters.pagerAdapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.mekanly.presentation.ui.fragments.flow.FlowFragment
import com.mekanly.presentation.ui.fragments.search.SearchSubFragment

class SearchViewAdapter (fragmentActivity: FragmentActivity) : FragmentStateAdapter(fragmentActivity) {

    override fun getItemCount(): Int {
        return 2 // Количество вкладок
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> SearchSubFragment()
            1 -> SearchSubFragment()

            else -> FlowFragment()
        }
    }
}