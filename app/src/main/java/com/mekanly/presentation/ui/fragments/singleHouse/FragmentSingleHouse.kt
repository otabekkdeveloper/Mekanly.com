package com.mekanly.presentation.ui.fragments.singleHouse

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mekanly.R
import com.mekanly.databinding.FragmentSingleHouseBinding
import com.mekanly.presentation.ui.adapters.AdapterInformationInSingleHouse
import com.mekanly.presentation.ui.adapters.HouseItem

class FragmentSingleHouse : Fragment() {

    private lateinit var binding: FragmentSingleHouseBinding
    private lateinit var houseAdapter: AdapterInformationInSingleHouse

    companion object {
        fun newInstance() = FragmentSingleHouse()
    }

    private val viewModel: VMSingleHouse by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {

        binding = FragmentSingleHouseBinding.inflate(inflater, container, false)


        setOnClickListener()


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



        return binding.root
    }

    private fun setOnClickListener() {

        binding.apply {

            btnBack.setOnClickListener{

                findNavController().popBackStack()

            }

        }



    }
}