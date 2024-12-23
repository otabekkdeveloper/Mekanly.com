package com.mekanly.presentation.ui.adapters.pagerAdapters



import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.mekanly.presentation.ui.fragments.businessProfiles.BusinessProfilesSubFragment
import com.mekanly.presentation.ui.fragments.flow.FragmentHome

class BildirishlerimTabLayout (fragmentActivity: FragmentActivity) : FragmentStateAdapter(fragmentActivity) {

    override fun getItemCount(): Int {
        return 1 // Количество вкладок
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> BusinessProfilesSubFragment()
            else -> FragmentHome()
        }
    }
}