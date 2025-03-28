package com.mekanly.presentation.ui.fragments.home


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.mekanly.R
import com.mekanly.data.DataTopNotifications
import com.mekanly.data.constants.Constants.Companion.UNSUCCESSFUL_RESPONSE
import com.mekanly.data.constants.Constants.Companion.getErrorMessageUpToType
import com.mekanly.data.dataModels.DataBanner
import com.mekanly.data.dataModels.DataHouse
import com.mekanly.data.responseBody.ResponseBodyState
import com.mekanly.databinding.FragmentHomeBinding
import com.mekanly.presentation.BannerType
import com.mekanly.presentation.ui.adapters.AdapterTopNotifications
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


class FragmentHome : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private lateinit var propertyAdapter: AdapterTopHouses
    private lateinit var adapterBanners: AdapterBanners
    private lateinit var adapterBigBanners: AdapterBigBanners


    private val viewModel: VMHome by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        observeViewModel()
        return binding.root
    }

    private fun observeViewModel() {
        lifecycleScope.launch {
            viewModel.homeState.collectLatest {
                when(it){
                    is FragmentHomeState.Error ->{
                        if (it.error==4){
                            binding.progressBar.visibility = View.GONE
                            Toast.makeText(requireContext(), getErrorMessageUpToType(requireContext(), UNSUCCESSFUL_RESPONSE),Toast.LENGTH_SHORT).show()
                        }else{
                            binding.progressBar.visibility = View.GONE
                            Toast.makeText(requireContext(), getErrorMessageUpToType(requireContext(),it.error as Int), Toast.LENGTH_SHORT).show()

                        }
                    }
                    FragmentHomeState.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                    }

                    is FragmentHomeState.SuccessBanners ->{
                        binding.progressBar.visibility = View.GONE
                        val bigBanners = it.dataResponse.filter { it.type == BannerType.BIG_BANNER.value }
                        val smallBanners = it.dataResponse.filter { it.type == BannerType.SMALL_BANNER.value }
                        val insideBanners = it.dataResponse.filter { it.type == BannerType.INSIDE_BANNER.value }

                        setUpBigBanners(bigBanners)
                        setupSmallBanners(smallBanners)
//                        setupInsideBanners(insideBanners)
                    }

                    is FragmentHomeState.SuccessTopHouses->{
                        binding.progressBar.visibility = View.GONE
                        if (it.dataResponse.isNotEmpty()){
                            setTopHousesAdapter(it.dataResponse)
                        }
                    }
                    else -> {}
                }
            }
        }

    }

    private fun setupSmallBanners(dataBanners: List<DataBanner>) {
        adapterBanners = AdapterBanners(dataBanners)
        binding.rvBanner.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.rvBanner.adapter = adapterBanners
        binding.rvBanner.isNestedScrollingEnabled = true
    }

    private fun setUpBigBanners(dataBanners: List<DataBanner>) {
        adapterBigBanners = AdapterBigBanners(dataBanners)
        binding.rvBigBanners.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.rvBigBanners.adapter = adapterBigBanners
        binding.rvBigBanners.isNestedScrollingEnabled = true
    }

    private fun setTopHousesAdapter(dataResponse: List<DataHouse>) {
        binding.progressBarTopHouses.visibility = View.GONE
        propertyAdapter = AdapterTopHouses(dataResponse, findNavController())
        binding.rvTopHouses.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.rvTopHouses.adapter = propertyAdapter
    }

}
