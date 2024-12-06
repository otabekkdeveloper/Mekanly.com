package com.mekanly

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayoutMediator
import com.mekanly.adapters.pagerAdapters.BildirishlerimTabLayout
import com.mekanly.databinding.FragmentBildirishlerimBinding


class BildirishlerimFragment : Fragment() {
    private lateinit var viewPagerAdapter: BildirishlerimTabLayout
    private lateinit var binding: FragmentBildirishlerimBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        binding = FragmentBildirishlerimBinding.inflate(inflater, container, false)


        // Настраиваем ViewPager2 с адаптером
        viewPagerAdapter = BildirishlerimTabLayout(requireActivity())
        binding.viewPager.adapter = viewPagerAdapter

        // Связываем TabLayout с ViewPager2 и добавляем иконки
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
        viewPager.isUserInputEnabled = false // Полное отключение свайпов
    }


}

