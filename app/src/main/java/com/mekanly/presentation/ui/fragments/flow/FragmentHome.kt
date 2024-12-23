package com.mekanly.presentation.ui.fragments.flow


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.mekanly.data.dataModels.DataHouse
import com.mekanly.data.responseBody.ResponseBodyState
import com.mekanly.databinding.FragmentFlowBinding
import com.mekanly.presentation.ui.adapters.PropertyAdapter
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


class FragmentHome : Fragment() {
    private lateinit var binding: FragmentFlowBinding
    private lateinit var propertyAdapter: PropertyAdapter

    private val viewModel: VMHome by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentFlowBinding.inflate(inflater, container, false)
        observeViewModel()
        return binding.root
    }

    private fun observeViewModel() {
        lifecycleScope.launch {
            viewModel.homeState.collectLatest {
                when(it){
                    is ResponseBodyState.Error ->{
                        binding.progressBar.visibility = View.GONE
                        Toast.makeText(requireContext(), it.error as String, Toast.LENGTH_SHORT).show()
                    }
                    ResponseBodyState.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                    }
                    is ResponseBodyState.SuccessList ->{
                        binding.progressBar.visibility = View.GONE
                        it.dataResponse as List<DataHouse>
                        setAdapter(it.dataResponse)
                    }
                    else -> {}
                }
            }
        }
    }

    private fun setAdapter(dataResponse: List<DataHouse>) {
        propertyAdapter = PropertyAdapter(dataResponse)
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.recyclerView.adapter = propertyAdapter
    }


}
