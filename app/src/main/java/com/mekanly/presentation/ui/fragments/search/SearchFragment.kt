package com.mekanly.presentation.ui.fragments.search

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayoutMediator
import com.mekanly.R
import com.mekanly.adapters.pagerAdapters.SearchViewAdapter
import com.mekanly.databinding.FragmentSearchBinding


class SearchFragment : Fragment() {
    private lateinit var viewPagerAdapter: SearchViewAdapter
    private lateinit var binding: FragmentSearchBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSearchBinding.inflate(inflater, container, false)


        // Настраиваем ViewPager2 с адаптером
        viewPagerAdapter = SearchViewAdapter(requireActivity())
        binding.viewPager.adapter = viewPagerAdapter

        // Связываем TabLayout с ViewPager2 и добавляем иконки
        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            when (position) {
                0 -> {
                    tab.text = "EMLÄKLER"
                }

                1 -> {
                    tab.text = "EMLÄK GÖZLEÝÄNLER"
                }
            }
        }.attach()

        binding.outlinedButton.setOnClickListener{
            findNavController().navigate(R.id.action_homeFragment_to_filterFragment)
        }


        disableSwipe(binding.viewPager)

        return binding.root
    }

    private fun disableSwipe(viewPager: ViewPager2) {
        viewPager.isUserInputEnabled = false // Полное отключение свайпов
    }




}

