package com.mekanly.presentation.ui.fragments.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mekanly.data.repository.RepositoryHouses.Companion.LIMIT_REGULAR
import com.mekanly.databinding.FragmentSearchBinding
import com.mekanly.presentation.ui.adapters.AdapterSmallAdvertisements
import com.mekanly.presentation.ui.fragments.search.viewModel.VMSearch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


class SearchFragment : Fragment() {

    private lateinit var binding: FragmentSearchBinding

    private val viewModel: VMSearch by viewModels()

    private var isLoading: Boolean = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentSearchBinding.inflate(inflater, container, false)
        observeViewModel()
        initListeners()
        return binding.root
    }

    private fun initListeners() {
        binding.rvSearch.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                val visibleItemCount = layoutManager.childCount
                val totalItemCount = layoutManager.itemCount
                val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()

                if (!isLoading) {
                    isLoading = true
                    if ((visibleItemCount + firstVisibleItemPosition) >= totalItemCount && firstVisibleItemPosition >= 0 && totalItemCount >= LIMIT_REGULAR) {
                        viewModel.getPageInfoDefault()
                    }
                }
            }
        })
    }

    private fun observeViewModel() {
        lifecycleScope.launch {
            viewModel.houses.collectLatest {
                isLoading = false
                setAdapter()
            }
        }
    }

    private fun setAdapter() {
        binding.rvSearch.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.rvSearch.adapter = AdapterSmallAdvertisements(viewModel.houses.value)
    }


}

