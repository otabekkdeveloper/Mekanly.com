package com.mekanly.presentation.ui.fragments.home


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.mekanly.data.constants.Constants
import com.mekanly.data.constants.Constants.Companion.UNSUCCESSFUL_RESPONSE
import com.mekanly.data.constants.Constants.Companion.getErrorMessageUpToType
import com.mekanly.data.dataModels.DataHouse
import com.mekanly.data.responseBody.ResponseBodyState
import com.mekanly.databinding.FragmentFlowBinding
import com.mekanly.presentation.ui.adapters.AdapterSmallAdvertisements
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


class FragmentHome : Fragment() {
    private lateinit var binding: FragmentFlowBinding
    private lateinit var propertyAdapter: AdapterSmallAdvertisements

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
                        if (it.error==4){
                            binding.progressBar.visibility = View.GONE
                            Toast.makeText(requireContext(), getErrorMessageUpToType(requireContext(), UNSUCCESSFUL_RESPONSE),Toast.LENGTH_SHORT).show()
                        }else{
                            binding.progressBar.visibility = View.GONE
                            Toast.makeText(requireContext(), getErrorMessageUpToType(requireContext(),it.error as Int), Toast.LENGTH_SHORT).show()

                        }
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
        propertyAdapter = AdapterSmallAdvertisements(dataResponse)
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.recyclerView.adapter = propertyAdapter
    }


}
