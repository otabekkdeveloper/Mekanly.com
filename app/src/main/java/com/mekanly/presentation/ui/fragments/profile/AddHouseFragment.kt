package com.mekanly.presentation.ui.fragments.profile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayoutMediator
import com.mekanly.presentation.ui.adapters.pagerAdapters.ProfileTabLayoutAdapter
import com.mekanly.databinding.FragmentJayGoshmakBinding


class AddHouseFragment : Fragment() {
    private lateinit var b : FragmentJayGoshmakBinding
    private lateinit var viewPagerAdapter : ProfileTabLayoutAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        b = FragmentJayGoshmakBinding.inflate(inflater, container, false)

        viewPagerAdapter = ProfileTabLayoutAdapter(requireActivity())
        b.viewPager.adapter = viewPagerAdapter

        TabLayoutMediator(b.tabLayout, b.viewPager){ tab, position ->
            when (position){
                0 -> {
                    tab.text = "Bildirişler"
                }
                1 -> {
                    tab.text = "Suratlar"
                }
                2 -> {
                    tab.text = "Maglumatlar"
                }
            }
        }.attach()

        disableSwipe(b.viewPager)

        return b.root
    }

    private fun disableSwipe(viewPager2: ViewPager2){
        viewPager2.isUserInputEnabled = false
    }
}