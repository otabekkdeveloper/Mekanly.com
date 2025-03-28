package com.mekanly.presentation.ui.business

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.mekanly.BusinessType
import com.mekanly.DataItemBusinessCategories
import com.mekanly.R
import com.mekanly.data.DataItemBusinessProfile
import com.mekanly.databinding.FragmentBusinessBinding
import com.mekanly.presentation.ui.adapters.AdapterItemBusinessCategories
import com.mekanly.presentation.ui.adapters.AdapterItemBusinessProfile
import com.mekanly.presentation.ui.fragments.flow.FragmentFlowDirections
import com.mekanly.presentation.ui.fragments.home.FragmentHomeDirections


class FragmentBusiness : Fragment() {
    private lateinit var binding: FragmentBusinessBinding
    private lateinit var businessProfileAdapter: AdapterItemBusinessCategories


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        binding = FragmentBusinessBinding.inflate(inflater, container, false)


        val items = listOf(
            DataItemBusinessProfile(
                R.drawable.placeholder, "Rowaç mebel", "Gyssagly satlyk jaý gerek!!!"
            ),
            DataItemBusinessProfile(R.drawable.home_villa_image, "Täze öý", "Jaý amatly we täze."),
            DataItemBusinessProfile(
                R.drawable.placeholder, "Rowaç mebel", "Gyssagly satlyk jaý gerek!!!"
            ),
            DataItemBusinessProfile(R.drawable.home_villa_image, "Täze öý", "Jaý amatly we täze."),
            DataItemBusinessProfile(
                R.drawable.placeholder, "Rowaç mebel", "Gyssagly satlyk jaý gerek!!!"
            ),
            DataItemBusinessProfile(R.drawable.home_villa_image, "Täze öý", "Jaý amatly we täze."),
            DataItemBusinessProfile(
                R.drawable.model_house, "Satlyk dükan", "Dükan doly enjamlaşdyrylan."
            )
        )

        val adapter = AdapterItemBusinessProfile(items)
        binding.rvBusinessProfiles.layoutManager = LinearLayoutManager(requireContext())
        binding.rvBusinessProfiles.adapter = adapter


        // Создание списка элементов
        val businessProfileItems = listOf(
            DataItemBusinessCategories(
                id = 1,
                title = "Gozgalmaýan\nemläkler",
                count = "1",
                imageResId = R.drawable.business_profile_image_house,
                type = BusinessType.REAL_ESTATE
            ),

            DataItemBusinessCategories(
                id = 2,
                title = "Mebel",
                count = "12",
                imageResId = R.drawable.divan,
                type = BusinessType.FURNITURE
            ),

            DataItemBusinessCategories(
                id = 3,
                title = "Öý hojalyk\n tehnikalary",
                count = "51",
                imageResId = R.drawable.washer_mashine,
                type = BusinessType.HOUSEHOLD_APPLIANCES
            ),

            DataItemBusinessCategories(
                id = 4,
                title = "Haly we dokma",
                count = "43",
                imageResId = R.drawable.carpet,
                type = BusinessType.CARPET_AND_RUGS
            ),

            DataItemBusinessCategories(
                id = 5,
                title = "Lýustralar we\n yşyklandyryjylar",
                count = "8",
                imageResId = R.drawable.image_chandelier,
                type = BusinessType.CHANDELIERS
            ),

            DataItemBusinessCategories(
                id = 6,
                title = "Gurluşyk",
                count = "32",
                imageResId = R.drawable.image_drill,
                type = BusinessType.CONSTRUCTION
            ),

        )

        // Создаем и устанавливаем адаптер
        val businessCategoryAdapter = AdapterItemBusinessCategories(businessProfileItems) { selectedItem ->
            // Навигация на SubFragment
            val action = FragmentFlowDirections.actionFragmentHomeToSubBusinessFragment(title = selectedItem.title ?: "N/A", businessType = selectedItem.type.name)

            findNavController().navigate(action)
        }

        // Настройка RecyclerView
        binding.categoriesRV.layoutManager = GridLayoutManager(requireContext(), 3)
        binding.categoriesRV.adapter = businessCategoryAdapter




//        // Настройка RecyclerView
//        businessProfileAdapter =
//            AdapterItemBusinessCategories(businessProfileItems) { clickedItem ->
//                // Обработка клика на элемент
//                Toast.makeText(
//                    requireContext(), "Clicked: ${clickedItem.title}", Toast.LENGTH_SHORT
//                ).show()
//            }
//
//        binding.categoriesRV.layoutManager =
//            GridLayoutManager(requireContext(), 3) // Например, сетка из 2 колонок
//        binding.categoriesRV.adapter = businessProfileAdapter






        return binding.root
    }


}