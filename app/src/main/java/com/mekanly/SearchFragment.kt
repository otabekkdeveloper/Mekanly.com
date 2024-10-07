package com.mekanly

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.tabs.TabLayoutMediator
import com.mekanly.adapters.SearchViewAdapter
import com.mekanly.databinding.FragmentSearchBinding


class SearchFragment : Fragment() {
    private lateinit var viewPagerAdapter: SearchViewAdapter
    private lateinit var binding: FragmentSearchBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
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







        return binding.root
    }

            }

