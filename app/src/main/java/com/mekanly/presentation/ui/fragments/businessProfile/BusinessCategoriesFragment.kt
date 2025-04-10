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
import com.mekanly.data.dataModels.DataBusinessProfileCategory
import com.mekanly.databinding.FragmentBusinessCategoriesBinding
import com.mekanly.presentation.ui.StaticFunctions.showErrorSnackBar
import com.mekanly.presentation.ui.adapters.AdapterItemBusinessCategories
import com.mekanly.presentation.ui.enums.BusinessType
import com.mekanly.presentation.ui.fragments.businessProfile.adapter.AdapterFilterBusinessCategories
import com.mekanly.presentation.ui.fragments.businessProfile.viewModel.FragmentBusinessProfileState
import com.mekanly.presentation.ui.fragments.businessProfile.viewModel.VMBusinessProfiles
import com.mekanly.presentation.ui.fragments.flow.FragmentFlowDirections
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


class BusinessCategoriesFragment : Fragment() {

    private lateinit var binding: FragmentBusinessCategoriesBinding
    private val viewModel by viewModels<VMBusinessProfiles>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentBusinessCategoriesBinding.inflate(inflater, container, false)


        observeViewModel()
        initListeners()



        return binding.root
    }

    private fun initListeners() {

        binding.apply {


            backBtn.setOnClickListener{
                findNavController().popBackStack()
            }



        }

    }

    private fun observeViewModel() {
        lifecycleScope.launch {
            viewModel.fragmentState.collectLatest {
                when(it){
                    is FragmentBusinessProfileState.Error -> {
//                        binding.progressBar.visibility = View.GONE
                        showErrorSnackBar(requireContext(),binding.root,it.error.toString())
                    }
                    FragmentBusinessProfileState.Loading -> {
//                        binding.progressBar.visibility = View.VISIBLE
                    }
                    is FragmentBusinessProfileState.SuccessCategories -> {
                        delay(200)
//                        binding.progressBar.visibility = View.GONE
                        setBusinessProfileCategoryAdapter(it.dataResponse)
                    }

                    else -> {}
                }
            }
        }}


    private fun setBusinessProfileCategoryAdapter(list: List<DataBusinessProfileCategory>) {
        val businessCategoryAdapter = AdapterFilterBusinessCategories(list) { selectedItem ->
            val action = BusinessCategoriesFragmentDirections.actionBusinessCategoriesFragment3Self(
                selectedItem.id.toString(), title = selectedItem.title ?: "N/A", businessType = selectedItem.type?.name
                ?: BusinessType.FURNITURE.name)

            findNavController().navigate(action)
        }
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.adapter = businessCategoryAdapter
    }



}