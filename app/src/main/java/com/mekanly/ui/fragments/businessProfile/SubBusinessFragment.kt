package com.mekanly.presentation.ui.fragments.businessProfile

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.mekanly.data.models.BusinessProfile
import com.mekanly.data.models.BusinessCategory
import com.mekanly.databinding.FragmentSubBusinessBinding
import com.mekanly.presentation.ui.adapters.AdapterItemBusinessProfile
import com.mekanly.presentation.ui.adapters.AdapterItemBusinessCategories
import com.mekanly.presentation.ui.enums.BusinessType
import com.mekanly.presentation.ui.fragments.businessProfile.viewModel.FragmentSubBusinessProfileState
import com.mekanly.presentation.ui.fragments.businessProfile.viewModel.VMSubBusinessProfile
import com.mekanly.presentation.ui.fragments.flow.FragmentFlowDirections
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


class SubBusinessFragment : Fragment() {
    private lateinit var binding: FragmentSubBusinessBinding
    private val args by navArgs<SubBusinessFragmentArgs>()
    private var currentBusinessType: BusinessType? = null
    private val viewModel by viewModels<VMSubBusinessProfile>()
    private var title: String? = "N/A"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        currentBusinessType = BusinessType.valueOf(args.businessType)
        title = args.title
        Log.e("TAG_business_type", "onCreate: test business type $currentBusinessType, title $title")
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentSubBusinessBinding.inflate(inflater, container, false)
        setUI()
        initListeners()
        getSimilarProfiles()
        getSubCategories()
        observeViewModel()
        return binding.root
    }

    private fun getSubCategories() {
        viewModel.getSimilarCategories(args.categoryId)
    }

    private fun observeViewModel() {
        lifecycleScope.launch {
        viewModel.fragmentState.collectLatest {
            when(it){
                is FragmentSubBusinessProfileState.Error -> {
                binding.progressBar.visibility = View.GONE
                }
                FragmentSubBusinessProfileState.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                }
                is FragmentSubBusinessProfileState.SuccessBusinessProfiles ->{
                    binding.progressBar.visibility = View.GONE
                    setRecycler(it.dataResponse)
                }

                is FragmentSubBusinessProfileState.SuccessCategories -> {
                    binding.progressBar.visibility = View.GONE
                    setSimilarCategoryRecycler(it.dataResponse)
                }

                else -> {}
            }
        }
        }
    }

    private fun setSimilarCategoryRecycler(dataResponse: List<BusinessCategory>) {
            val businessCategoryAdapter = AdapterItemBusinessCategories(dataResponse) { selectedItem ->
                val action = FragmentFlowDirections.actionFragmentHomeToSubBusinessFragment(selectedItem.id,title = selectedItem.title ?: "N/A", businessType = selectedItem.type?.name
                    ?: BusinessType.FURNITURE.name)

                findNavController().navigate(action)
            }
            binding.rvCategories.layoutManager = GridLayoutManager(requireContext(), 3)
            binding.rvCategories.adapter = businessCategoryAdapter
    }

    private fun getSimilarProfiles() {
        viewModel.getSimilarBusinessProfiles(args.categoryId.toLong())
    }

    private fun setRecycler(items: List<BusinessProfile>) {
        val adapter = AdapterItemBusinessProfile(items)
        binding.rvSimilarBusinessProfiles.layoutManager = LinearLayoutManager(requireContext())
        binding.rvSimilarBusinessProfiles.adapter = adapter
    }

    private fun setUI() {
        binding.apply {
            toolbarTitle.text = title ?: "N/A"
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.rvCategories.apply {
            layoutManager = GridLayoutManager(requireContext(), 3)
            this.adapter = adapter
        }
    }

    private fun initListeners(){

        binding.backBtn.setOnClickListener{

            findNavController().popBackStack()

        }

    }



}