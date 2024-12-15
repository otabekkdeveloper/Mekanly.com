package com.mekanly.presentation.ui.fragments.businessProfiles

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayoutMediator
import com.mekanly.presentation.ui.adapters.pagerAdapters.BildirishlerimTabLayout
import com.mekanly.databinding.FragmentBildirishlerimBinding

class BusinessProfilesFragment : Fragment() {
    private lateinit var viewPagerAdapter: BildirishlerimTabLayout
    private lateinit var binding: FragmentBildirishlerimBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentBildirishlerimBinding.inflate(inflater, container, false)
        viewPagerAdapter = BildirishlerimTabLayout(requireActivity())
        binding.viewPager.adapter = viewPagerAdapter

        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            when (position) {
                0 -> {
                    tab.text = "Emläklerim"
                }

                1 -> {
                    tab.text = "Gözleýän emläklerim"
                }
            }
        }.attach()

        disableSwipe(binding.viewPager)

        return binding.root
    }

    private fun disableSwipe(viewPager: ViewPager2) {
        viewPager.isUserInputEnabled = false
    }

}