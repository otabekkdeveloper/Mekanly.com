package com.mekanly.presentation.ui.fragments.home


import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.mekanly.utils.Constants.Companion.UNSUCCESSFUL_RESPONSE
import com.mekanly.utils.Constants.Companion.getErrorMessageUpToType
import com.mekanly.data.models.Banner
import com.mekanly.data.models.TopHouses
import com.mekanly.databinding.FragmentHomeBinding
import com.mekanly.presentation.ui.enums.BannerType
import com.mekanly.ui.fragments.home.AdapterBanners
import com.mekanly.ui.fragments.home.AdapterTopHouses
import com.mekanly.ui.fragments.home.FragmentHomeState
import com.mekanly.ui.fragments.home.VMHome
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


class FragmentHome : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private lateinit var propertyAdapter: AdapterTopHouses
    private lateinit var adapterBanners: AdapterBanners
    private lateinit var adapterBigBanners: AdapterBigBanners


    private var currentBigBannerPosition = 0
    private val bigBannerScrollHandler = Handler(Looper.getMainLooper())
    private val bigBannerScrollRunnable = object : Runnable {
        override fun run() {
            val itemCount = adapterBigBanners.itemCount
            if (itemCount == 0) return

            currentBigBannerPosition = (currentBigBannerPosition + 1) % itemCount
            binding.rvBigBanners.smoothScrollToPosition(currentBigBannerPosition)

            bigBannerScrollHandler.postDelayed(this, 5000) // каждые 4 секунды
        }
    }


    private val bannerScrollHandler = Handler(Looper.getMainLooper())
    private val bannerScrollRunnable = object : Runnable {
        override fun run() {
            val layoutManager = binding.rvBanner.layoutManager as LinearLayoutManager
            val itemCount = adapterBanners.itemCount

            if (itemCount == 0) return

            val scrollAmountPx = binding.rvBanner.width / 3 + 200 // подбери под свой баннер

            binding.rvBanner.smoothScrollBy(scrollAmountPx, 0)

            val lastVisible = layoutManager.findLastCompletelyVisibleItemPosition()
            if (lastVisible == itemCount - 1) {
                binding.rvBanner.scrollToPosition(0) // сброс в начало
            }

            bannerScrollHandler.postDelayed(this, 3000) // пауза 4 сек
        }
    }






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
                        binding.textTopHouses.visibility = View.VISIBLE
                        if (it.dataResponse.isNotEmpty()){
                            setTopHousesAdapter(it.dataResponse)

                        }
                    }
                    else -> {}
                }
            }
        }

    }

    private fun setupSmallBanners(dataBanners: List<Banner>) {
        adapterBanners = AdapterBanners(dataBanners)
        binding.rvBanner.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.rvBanner.adapter = adapterBanners
        binding.rvBanner.isNestedScrollingEnabled = true

        bannerScrollHandler.postDelayed(bannerScrollRunnable, 3000)
    }







    private fun setUpBigBanners(dataBanners: List<Banner>) {
        adapterBigBanners = AdapterBigBanners(dataBanners)
        binding.rvBigBanners.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.rvBigBanners.adapter = adapterBigBanners
        binding.rvBigBanners.isNestedScrollingEnabled = true

        // Запускаем автопрокрутку
        bigBannerScrollHandler.postDelayed(bigBannerScrollRunnable, 5000)
    }


    private fun setTopHousesAdapter(dataResponse: List<TopHouses>) {
        if (!::propertyAdapter.isInitialized) {
            propertyAdapter = AdapterTopHouses(findNavController())
            binding.rvTopHouses.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            binding.rvTopHouses.adapter = propertyAdapter
        }
        propertyAdapter.setItems(dataResponse)
    }


    override fun onDestroyView() {
        super.onDestroyView()
        bannerScrollHandler.removeCallbacks(bannerScrollRunnable)
        bigBannerScrollHandler.removeCallbacks(bigBannerScrollRunnable)
    }







}
