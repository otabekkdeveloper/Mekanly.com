package com.mekanly.presentation.ui.fragments.singleHouse

import android.annotation.SuppressLint
import android.graphics.Paint
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
import com.mekanly.data.models.House
import com.mekanly.data.models.Possibility
import com.mekanly.domain.model.ResponseBodyState
import com.mekanly.databinding.FragmentSingleHouseBinding
import com.mekanly.presentation.ui.adapters.AdapterInformationInSingleHouse
import com.mekanly.presentation.ui.adapters.HouseItem
import com.mekanly.presentation.ui.fragments.singleHouse.adapter.AdapterPossibilities
import com.mekanly.utils.extensions.showErrorSnackBar
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Locale

class FragmentSingleHouse : Fragment() {

    private lateinit var binding: FragmentSingleHouseBinding
    private val args by navArgs<FragmentSingleHouseArgs>()
    private lateinit var houseAdapter: AdapterInformationInSingleHouse
    private lateinit var opportunityAdapter: AdapterPossibilities


    private val viewModel: VMSingleHouse by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentSingleHouseBinding.inflate(inflater, container, false)
        getHouseInfo()
        initListeners()
        observeViewModel()
        return binding.root
    }

    private fun observeViewModel() {
        /**To observe state changes in the viewModel**/
        lifecycleScope.launch {
            viewModel.singleHouseState.collectLatest {
                when (it) {
                    is ResponseBodyState.Error -> {
                        requireContext().showErrorSnackBar(binding.root, it.error.toString())
                        binding.progressBar.visibility = View.GONE
                    }

                    ResponseBodyState.Loading -> {
                        binding.progressBar.visibility = View.VISIBLE
                    }

                    is ResponseBodyState.Success -> {
                        delay(200)
                        binding.progressBar.visibility = View.GONE
                        setViewPager(it.dataResponse as House)
                        setHouseDetails(it.dataResponse)
                        setPossibilityAdapter(it.dataResponse.possibilities)
                        setHomeDetails(it.dataResponse)
                    }

                    else -> {}
                }
            }
        }
    }

    private fun setPossibilityAdapter(possibilities: List<Possibility>) {
        opportunityAdapter = AdapterPossibilities(possibilities)
        binding.rvOpportunity.layoutManager = GridLayoutManager(requireContext(), 2)
        binding.rvOpportunity.adapter = opportunityAdapter
    }

    @SuppressLint("SetTextI18n")
    private fun setHouseDetails(house: House) {
        binding.apply{
            tvTitle.text = house.name
            tvDate.text = formatDate(house.createdAt)
            tvDetails.text = house.description
            tvHouseType.text = house.categoryName
            tvViewCount.text = house.viewed.toString()
            tvAddress.text =house.location.name
        }

    }

    private fun setViewPager(house: House) {
        val imageSliderAdapter = HouseImagesAdapter(house.images)
        binding.viewPager.adapter = imageSliderAdapter
    }

    private fun setHomeDetails(dataResponse: House) {
        val houseList = listOf(
            HouseItem(R.drawable.ic_houses_for_sale, "Bölümi", dataResponse.categoryName),
            HouseItem(R.drawable.location_icon, "Ýerleşýän ýeri", dataResponse.location.name),
            HouseItem(R.drawable.ic_calendar, "Goýlan senesi", formatDate(dataResponse.createdAt)),
            HouseItem(R.drawable.ic_phone, "Telefon nomeri", dataResponse.bronNumber),
            HouseItem(R.drawable.elitka, "Emläk görnüşi", dataResponse.categoryName),
            HouseItem(R.drawable.ic_count_room, "Otag sany", dataResponse.roomNumber.toString()),
            HouseItem(R.drawable.ic_number_of_floors, "Gat sany", dataResponse.floorNumber.toString()),
//            HouseItem(R.drawable.elitka, "Remont görnüşi", dataResponse.luxe.toString()),
//            HouseItem(R.drawable.ic_total_area, "Umumy meýdany", "Not available"),
            HouseItem(R.drawable.ic_price, "Bahasy", "${dataResponse.price} TMT")
        )

        houseAdapter = AdapterInformationInSingleHouse(houseList)
        binding.rvSingleHouse.layoutManager = GridLayoutManager(requireContext(), 2)
        binding.rvSingleHouse.adapter = houseAdapter


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
        binding.btnComments.setOnClickListener{
            showCommentsBottomSheet()
        }

        binding.btnReport.paintFlags = binding.btnReport.paintFlags or Paint.UNDERLINE_TEXT_FLAG
    }

    private fun showCommentsBottomSheet() {
        val action =
            FragmentSingleHouseDirections.actionFragmentSingleHouseToBottomSheetComments(args.houseId)
        findNavController().navigate(action)
    }

    private fun formatDate(dateString: String): String {
        val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSSSS'Z'", Locale.getDefault()) // формат входной строки
        val outputFormat = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault()) // нужный формат: день месяц год

        val date = inputFormat.parse(dateString) // парсим строку в объект Date
        return outputFormat.format(date)
    }


}