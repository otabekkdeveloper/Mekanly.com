package com.mekanly.presentation.ui.fragments.singleHouse

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import com.mekanly.R
import com.mekanly.data.dataModels.DataHouse
import com.mekanly.data.responseBody.ResponseBodyState
import com.mekanly.databinding.FragmentSingleHouseBinding
import com.mekanly.presentation.ui.StaticFunctions.showErrorSnackBar
import com.mekanly.presentation.ui.adapters.AdapterInformationInSingleHouse
import com.mekanly.presentation.ui.adapters.AdapterOpportunityInSingleHouse
import com.mekanly.presentation.ui.adapters.HouseItem
import com.mekanly.presentation.ui.adapters.OpportunityItem
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class FragmentSingleHouse : Fragment() {

    private lateinit var binding: FragmentSingleHouseBinding
    private val args by navArgs<FragmentSingleHouseArgs>()
    private lateinit var houseAdapter: AdapterInformationInSingleHouse
    private lateinit var opportunityAdapter: AdapterOpportunityInSingleHouse


    private val viewModel: VMSingleHouse by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {

        binding = FragmentSingleHouseBinding.inflate(inflater, container, false)
        getHouseInfo()
        initListeners()
        observeViewModel()
        setBulkyData()
        return binding.root
    }

    private fun observeViewModel() {
        /**To observe state changes in the viewModel**/
        lifecycleScope.launch {
            viewModel.singleHouseState.collectLatest {
                when (it) {
                    is ResponseBodyState.Error -> {
                        showErrorSnackBar(requireContext(), binding.root, it.error.toString())
                        binding.progressBar.visibility = View.GONE
                    }

                    ResponseBodyState.Loading -> {
                        binding.progressBar.visibility = View.VISIBLE
                    }

                    is ResponseBodyState.Success -> {
                        binding.progressBar.visibility = View.GONE
                        setViewPager(it.dataResponse as DataHouse)
                        setHouseDetails(it.dataResponse)
                    }

                    else -> {}
                }
            }
        }
    }

    private fun setHouseDetails(dataHouse: DataHouse) {
        binding.apply{
            tvTitle.text = dataHouse.name
            tvDetails.text = dataHouse.description
            tvHouseType.text = dataHouse.categoryName
            tvAddress.text =dataHouse.location.name
        }

    }

    private fun setViewPager(dataHouse: DataHouse) {
        val imageSliderAdapter = HouseImagesAdapter(dataHouse.images)
        binding.viewPager.adapter = imageSliderAdapter
    }

    private fun setBulkyData() {
        val houseList = listOf(
            HouseItem(R.drawable.ic_houses_for_sale, "Bölümi", "Satlyk jaýlar"),
            HouseItem(R.drawable.location_icon, "Ýerleşýän ýeri", "Aşgabat/mir1"),
            HouseItem(R.drawable.ic_calendar, "Goýlan senesi", "09.12.2023"),
            HouseItem(R.drawable.ic_phone, "Telefon nomeri", "+99364652712"),
            HouseItem(R.drawable.elitka, "Emläk görnüşi", "Elitga"),
            HouseItem(R.drawable.ic_count_room, "Otag sany", "1"),
            HouseItem(R.drawable.ic_number_of_floors, "Gat sany", "5/12"),
            HouseItem(R.drawable.elitka, "Remont görnüşi", "Ýewroremont"),
            HouseItem(R.drawable.ic_total_area, "Umumy meýdany", "200 m2"),
            HouseItem(R.drawable.ic_price, "Bahasy", "100000 TMT")
        )

        houseAdapter = AdapterInformationInSingleHouse(houseList)
        binding.rvSingleHouse.layoutManager = GridLayoutManager(requireContext(), 2)
        binding.rvSingleHouse.adapter = houseAdapter



        val opportunityList = listOf(
            OpportunityItem(R.drawable.ic_wifi, "Wi-Fi"),
            OpportunityItem(R.drawable.dush_ic, "Duş"),
            OpportunityItem(R.drawable.kitchen_ic, "Aşhana"),
            OpportunityItem(R.drawable.pech_ic, "Peç"),
            OpportunityItem(R.drawable.kir_mashyn_ic, "Kir maşyn"),
            OpportunityItem(R.drawable.lift_ic, "Lift"),
            OpportunityItem(R.drawable.ic_tv, "Telewizor"),
            OpportunityItem(R.drawable.ic_balcony, "Balkon"),
            OpportunityItem(R.drawable.kondisioner_ic, "Kondisioner"),
            OpportunityItem(R.drawable.kitchen_furniture_ic, "Aşhana-mebel"),
            OpportunityItem(R.drawable.ic_refrigerator, "Sowadyjy"),
            OpportunityItem(R.drawable.ic_swimming_pool, "Basseýn"),
            OpportunityItem(R.drawable.ic_bedroom, "Spalny"),
            OpportunityItem(R.drawable.ish_stoly_ic, "Iş stoly"),
            OpportunityItem(R.drawable.mebel_ic, "Mebel şkaf"),
            OpportunityItem(R.drawable.mangal_ic, "Mangal"),
            OpportunityItem(R.drawable.gyzgyn_suw_ic, "Gyzgyn suw"),
            OpportunityItem(R.drawable.ic_heating_system, "Ýyladyş ylgamy")
        )

        opportunityAdapter = AdapterOpportunityInSingleHouse(opportunityList)
        binding.rvOpportunity.layoutManager = GridLayoutManager(requireContext(), 2)
        binding.rvOpportunity.adapter = opportunityAdapter


    }

    private fun getHouseInfo() {
        if (args.houseId == -1L) {
            return
        } else {
            viewModel.getHouseDetails(args.houseId)
        }

    }

    private fun initListeners() {
        binding.btnBack.setOnClickListener {
            findNavController().popBackStack()
            }
    }
}