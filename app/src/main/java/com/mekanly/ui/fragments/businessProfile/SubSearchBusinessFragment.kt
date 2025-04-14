package com.mekanly.presentation.ui.fragments.businessProfile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.tabs.TabLayoutMediator
import com.mekanly.databinding.FragmentSubSearchBusinessBinding
import com.mekanly.presentation.ui.adapters.pagerAdapters.BusinessSearchViewPager
import com.mekanly.presentation.ui.bottomSheet.BusinessFilterFragmentBottomSheet


class SubSearchBusinessFragment : Fragment() {

    private lateinit var binding: FragmentSubSearchBusinessBinding
    private lateinit var viewPagerAdapter: BusinessSearchViewPager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {

        binding = FragmentSubSearchBusinessBinding.inflate(layoutInflater, container, false)

        initListeners()
        viewPager()


        return binding.root
    }


    private fun viewPager() {


        viewPagerAdapter = BusinessSearchViewPager(requireActivity())
        binding.viewPager.adapter = viewPagerAdapter

        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            when (position) {
                0 -> {
                    tab.text = "Bildirişler"
                }

                1 -> {
                    tab.text = "Biznes profiller"
                }

            }
        }.attach()
    }

    private fun initListeners() {

        binding.apply {

            backBtn.setOnClickListener {
                findNavController().popBackStack()
            }


            btnFilter.setOnClickListener{

                showBusinessFilterBottomSheet()

            }


        }


    }

    private fun showBusinessFilterBottomSheet() {
        val businessFilterBottomSheet = BusinessFilterFragmentBottomSheet.newInstance(
            onDelete = {
                // Обработка сброса фильтров
                Toast.makeText(requireContext(), "Фильтры сброшены", Toast.LENGTH_SHORT).show()
            }
        )

        // Показываем BottomSheet, используя parentFragmentManager
        businessFilterBottomSheet.show(parentFragmentManager, "BusinessFilterBottomSheet")
    }



}