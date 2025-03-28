package com.mekanly.presentation.ui.fragments.businessProfile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.mekanly.R
import com.mekanly.data.DataItemBusinessProfile
import com.mekanly.data.dataModels.DataBusinessProfileCategory
import com.mekanly.data.responseBody.ResponseBodyState
import com.mekanly.databinding.FragmentBusinessBinding
import com.mekanly.presentation.ui.StaticFunctions.showErrorSnackBar
import com.mekanly.presentation.ui.adapters.AdapterItemBusinessCategories
import com.mekanly.presentation.ui.adapters.AdapterItemBusinessProfile
import com.mekanly.presentation.ui.fragments.businessProfile.viewModel.VMBusinessProfiles
import com.mekanly.presentation.ui.fragments.flow.FragmentFlowDirections
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


class FragmentBusiness : Fragment() {
    private lateinit var binding: FragmentBusinessBinding
    private lateinit var businessProfileAdapter: AdapterItemBusinessCategories
    private val viewModel by viewModels<VMBusinessProfiles>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentBusinessBinding.inflate(inflater, container, false)
        setMockData()
        observeViewModel()
        return binding.root
    }

    private fun observeViewModel() {
        lifecycleScope.launch {
            viewModel.fragmentState.collectLatest {
                when(it){
                    is ResponseBodyState.Error -> {
                        binding.progressBar.visibility = View.GONE
                        showErrorSnackBar(requireContext(),binding.root,it.error.toString())
                    }
                    ResponseBodyState.Loading -> {
                        binding.progressBar.visibility = View.VISIBLE
                    }
                    is ResponseBodyState.SuccessList -> {
                        binding.progressBar.visibility = View.GONE
                        setBusinessProfileCategoryAdapter(it.dataResponse as List<DataBusinessProfileCategory>)
                    }
                    else -> {}
                }
            }
        }
    }

    private fun setBusinessProfileCategoryAdapter(list: List<DataBusinessProfileCategory>) {
        val businessCategoryAdapter = AdapterItemBusinessCategories(list) { selectedItem ->
            val action = FragmentFlowDirections.actionFragmentHomeToSubBusinessFragment(title = selectedItem.title ?: "N/A", businessType = selectedItem.type.name)

            findNavController().navigate(action)
        }
        binding.categoriesRV.layoutManager = GridLayoutManager(requireContext(), 3)
        binding.categoriesRV.adapter = businessCategoryAdapter
    }

    private fun setMockData() {
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


    }


}