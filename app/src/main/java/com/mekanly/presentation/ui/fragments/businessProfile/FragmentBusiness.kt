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
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mekanly.R
import com.mekanly.data.DataItemBusinessProfile
import com.mekanly.data.dataModels.DataBusinessProfileCategory
import com.mekanly.data.repository.RepositoryHouses.Companion.LIMIT_REGULAR
import com.mekanly.databinding.FragmentBusinessBinding
import com.mekanly.presentation.ui.StaticFunctions.showErrorSnackBar
import com.mekanly.presentation.ui.adapters.AdapterItemBusinessCategories
import com.mekanly.presentation.ui.enums.BusinessType
import com.mekanly.presentation.ui.fragments.businessProfile.adapter.AdapterBusinessProfilesPaginated
import com.mekanly.presentation.ui.fragments.businessProfile.viewModel.FragmentBusinessProfileState
import com.mekanly.presentation.ui.fragments.businessProfile.viewModel.VMBusinessProfiles
import com.mekanly.presentation.ui.fragments.flow.FragmentFlowDirections
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


class FragmentBusiness : Fragment() {
    private lateinit var binding: FragmentBusinessBinding
    private val viewModel by viewModels<VMBusinessProfiles>()
    private var adapter: AdapterBusinessProfilesPaginated? = null



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentBusinessBinding.inflate(inflater, container, false)
        initListeners()
        observeViewModel()
        return binding.root
    }

    private fun initListeners() {
        binding.rvBusinessProfiles.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                val visibleItemCount = layoutManager.childCount
                val totalItemCount = layoutManager.itemCount
                val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()

                if (!viewModel.getLoadingState()) {
                    Log.e("Pagination", "onScrolled: " + viewModel.businessProfiles.value.size)
                    if ((visibleItemCount + firstVisibleItemPosition) >= totalItemCount && firstVisibleItemPosition >= 0 && totalItemCount >= LIMIT_REGULAR) {
                        viewModel.getPageInfoDefault(viewModel.businessProfiles.value.size)
                    }

                }


            }
        })
    }

    private fun observeViewModel() {
        lifecycleScope.launch {
            viewModel.fragmentState.collectLatest {
                when(it){
                    is FragmentBusinessProfileState.Error -> {
                        binding.progressBar.visibility = View.GONE
                        showErrorSnackBar(requireContext(),binding.root,it.error.toString())
                    }
                    FragmentBusinessProfileState.Loading -> {
                        binding.progressBar.visibility = View.VISIBLE
                    }
                    is FragmentBusinessProfileState.SuccessCategories -> {
                        binding.progressBar.visibility = View.GONE
                        setBusinessProfileCategoryAdapter(it.dataResponse)
                    }

                    is FragmentBusinessProfileState.SuccessBusinessProfiles ->{
                        binding.progressBar.visibility = View.GONE
                        setAdapter()
                    }
                    else -> {}
                }
            }
        }

        lifecycleScope.launch {
            viewModel.businessProfiles.collectLatest {
                adapter =
                    AdapterBusinessProfilesPaginated(viewModel.businessProfiles.value, viewModel, findNavController())
                binding.rvBusinessProfiles.layoutManager =
                    LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
                binding.rvBusinessProfiles.adapter = adapter
            }
        }
    }

    private fun setBusinessProfileCategoryAdapter(list: List<DataBusinessProfileCategory>) {
        val businessCategoryAdapter = AdapterItemBusinessCategories(list) { selectedItem ->
            val action = FragmentFlowDirections.actionFragmentHomeToSubBusinessFragment(selectedItem.id,title = selectedItem.title ?: "N/A", businessType = selectedItem.type?.name
                ?: BusinessType.FURNITURE.name)

            findNavController().navigate(action)
        }
        binding.categoriesRV.layoutManager = GridLayoutManager(requireContext(), 3)
        binding.categoriesRV.adapter = businessCategoryAdapter
    }


    private fun setAdapter() {
        if (adapter == null) {
            adapter = AdapterBusinessProfilesPaginated(viewModel.businessProfiles.value, viewModel, findNavController())
            binding.rvBusinessProfiles.layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            binding.rvBusinessProfiles.adapter = adapter
        } else {
            adapter?.updateList()
        }
    }


}