package com.mekanly.adapters



import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.mekanly.BildirshlerimOneFragment
import com.mekanly.BildirshlerimTwoFragment
import com.mekanly.FlowFragment
import com.mekanly.ProfilePage.BildirishlerProfileFragment
import com.mekanly.ProfilePage.SuratlarProfileFragment

class BildirishlerimTabLayout (fragmentActivity: FragmentActivity) : FragmentStateAdapter(fragmentActivity) {

    override fun getItemCount(): Int {
        return 2 // Количество вкладок
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> BildirshlerimOneFragment()
            1 -> BildirshlerimTwoFragment()


            else -> FlowFragment()
        }
    }
}