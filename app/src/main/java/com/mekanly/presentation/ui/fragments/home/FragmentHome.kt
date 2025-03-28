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
import com.mekanly.R
import com.mekanly.data.DataTopNotifications
import com.mekanly.data.constants.Constants.Companion.UNSUCCESSFUL_RESPONSE
import com.mekanly.data.constants.Constants.Companion.getErrorMessageUpToType
import com.mekanly.data.dataModels.DataBanner
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

        adapterTopNotifications()




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
                        it.dataResponse as List<DataBanner>

                        val bigBanners = it.dataResponse.filter { it.type == BannerType.BIG_BANNER.value }
                        val smallBanners = it.dataResponse.filter { it.type == BannerType.SMALL_BANNER.value }
                        val insideBanners = it.dataResponse.filter { it.type == BannerType.INSIDE_BANNER.value }

                        setUpBigBanners(bigBanners)
                        setupSmallBanners(smallBanners)
//                        setupInsideBanners(insideBanners)
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

//    private fun setAdapter(dataResponse: List<DataHouse>) {
//        propertyAdapter = AdapterTopHouses(dataResponse)
//        binding.rvHouses.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
//        binding.rvHouses.adapter = propertyAdapter
//    }

    private fun adapterTopNotifications() {

        val items = listOf(

            DataTopNotifications(R.drawable.home_villa_image, "02. 08.2024", "Aşgabat/ mkr1,", "Satlyk jaý", "Gyssagly satlyk jaý gerek!!! HEMME şerti bolmaly amatly bahadan gerek jjbdjbdçbdfejffifrijirjif...", "10000 TMT" ),
            DataTopNotifications(R.drawable.home_villa_image, "02. 08.2024", "Aşgabat/ mkr1,", "Satlyk jaý", "Gyssagly satlyk jaý gerek!!! HEMME şerti bolmaly amatly bahadan gerek jjbdjbdçbdfejffifrijirjif...", "10000 TMT" ),
            DataTopNotifications(R.drawable.home_villa_image, "02. 08.2024", "Aşgabat/ mkr1,", "Satlyk jaý", "Gyssagly satlyk jaý gerek!!! HEMME şerti bolmaly amatly bahadan gerek jjbdjbdçbdfejffifrijirjif...", "10000 TMT" ),
            DataTopNotifications(R.drawable.home_villa_image, "02. 08.2024", "Aşgabat/ mkr1,", "Satlyk jaý", "Gyssagly satlyk jaý gerek!!! HEMME şerti bolmaly amatly bahadan gerek jjbdjbdçbdfejffifrijirjif...", "10000 TMT" ),
            DataTopNotifications(R.drawable.home_villa_image, "02. 08.2024", "Aşgabat/ mkr1,", "Satlyk jaý", "Gyssagly satlyk jaý gerek!!! HEMME şerti bolmaly amatly bahadan gerek jjbdjbdçbdfejffifrijirjif...", "10000 TMT" ),
            DataTopNotifications(R.drawable.home_villa_image, "02. 08.2024", "Aşgabat/ mkr1,", "Satlyk jaý", "Gyssagly satlyk jaý gerek!!! HEMME şerti bolmaly amatly bahadan gerek jjbdjbdçbdfejffifrijirjif...", "10000 TMT" ),
            DataTopNotifications(R.drawable.home_villa_image, "02. 08.2024", "Aşgabat/ mkr1,", "Satlyk jaý", "Gyssagly satlyk jaý gerek!!! HEMME şerti bolmaly amatly bahadan gerek jjbdjbdçbdfejffifrijirjif...", "10000 TMT" ),
            DataTopNotifications(R.drawable.home_villa_image, "02. 08.2024", "Aşgabat/ mkr1,", "Satlyk jaý", "Gyssagly satlyk jaý gerek!!! HEMME şerti bolmaly amatly bahadan gerek jjbdjbdçbdfejffifrijirjif...", "10000 TMT" ),
            DataTopNotifications(R.drawable.home_villa_image, "02. 08.2024", "Aşgabat/ mkr1,", "Satlyk jaý", "Gyssagly satlyk jaý gerek!!! HEMME şerti bolmaly amatly bahadan gerek jjbdjbdçbdfejffifrijirjif...", "10000 TMT" ),
            DataTopNotifications(R.drawable.home_villa_image, "02. 08.2024", "Aşgabat/ mkr1,", "Satlyk jaý", "Gyssagly satlyk jaý gerek!!! HEMME şerti bolmaly amatly bahadan gerek jjbdjbdçbdfejffifrijirjif...", "10000 TMT" ),
            DataTopNotifications(R.drawable.home_villa_image, "02. 08.2024", "Aşgabat/ mkr1,", "Satlyk jaý", "Gyssagly satlyk jaý gerek!!! HEMME şerti bolmaly amatly bahadan gerek jjbdjbdçbdfejffifrijirjif...", "10000 TMT" )

        )

        val adapter = AdapterTopNotifications(items)
        binding.topNotifications.layoutManager = LinearLayoutManager(requireContext())
        binding.topNotifications.adapter = adapter




    }

}
