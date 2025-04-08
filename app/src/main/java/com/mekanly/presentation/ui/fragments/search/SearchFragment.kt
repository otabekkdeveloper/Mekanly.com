package com.mekanly.presentation.ui.fragments.search

import LocationBottomSheet
import android.annotation.SuppressLint
import android.content.Context
import android.content.res.ColorStateList
import android.os.Bundle
import android.text.InputType
import android.util.Log
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mekanly.R
import com.mekanly.data.repository.RepositoryHouses.Companion.LIMIT_REGULAR
import com.mekanly.data.responseBody.ResponseBodyState
import com.mekanly.databinding.FragmentSearchBinding
import com.mekanly.presentation.ui.adapters.AdapterAdvertisements
import com.mekanly.presentation.ui.bottomSheet.PriceFilterBottomSheet
import com.mekanly.presentation.ui.bottomSheet.SectionSelectionBottomSheet
import com.mekanly.presentation.ui.fragments.flow.VMFlow
import com.mekanly.presentation.ui.fragments.search.viewModel.VMSearch
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


@Suppress("NAME_SHADOWING")
class SearchFragment : Fragment() {

    private lateinit var binding: FragmentSearchBinding

    private val viewModel: VMSearch by viewModels()

    private var isLoading: Boolean = false

    private var adapter: AdapterAdvertisements? = null

    private val vmFlow: VMFlow by activityViewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentSearchBinding.inflate(inflater, container, false)
        observeViewModel()
        setOnClickListener()
        initListeners()
        return binding.root
    }


    private fun setOnClickListener() {
        if (vmFlow.globalState.value.locations.isNotEmpty()){
            val cities = vmFlow.globalState.value.locations

            binding.btnLocations.setOnClickListener {
                val bottomSheet = LocationBottomSheet(cities, onDelete = {
                    binding.apply {
                        nameLocations.text = "Ýerleşýän ýeri"
                        nameLocations.setTextColor(ContextCompat.getColor(requireContext(), R.color.text_color_gray))
                        btnLocations.setBackgroundResource(R.drawable.bg_unselected_in_search_fragment)
                        icDown.setColorFilter(resources.getColor(R.color.text_color_gray))
                        icLocation.setColorFilter(resources.getColor(R.color.text_color_gray))
                    }
                }) { selectedCity ->
                    binding.apply {
                        adapter=null
                        nameLocations.text = selectedCity.name
                        btnLocations.setBackgroundResource(R.drawable.bg_selected_search_fragment)
                        icLocation.setColorFilter(resources.getColor(R.color.selected_color_in_search_fragment))
                        icDown.setColorFilter(resources.getColor(R.color.selected_color_in_search_fragment))
                        nameLocations.setTextColor(
                            ContextCompat.getColor(
                                requireContext(), R.color.selected_color_in_search_fragment
                            )
                        )
                    }
                    viewModel.getPageInfoDefault(0,selectedCity)
                }

                bottomSheet.show(childFragmentManager, "LocationBottomSheet")
            }

        }


        binding.btnFilter.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_filterFragment)
        }

        binding.btnCategories.setOnClickListener {
            val bottomSheet = SectionSelectionBottomSheet(onDelete = {
                binding.apply {
                    textCategory.text = "Kategoriya"
                    textCategory.setTextColor(ContextCompat.getColor(requireContext(), R.color.text_color_gray))
                    btnCategories.setBackgroundResource(R.drawable.bg_unselected_in_search_fragment)
                    icDownCategories.setColorFilter(resources.getColor(R.color.text_color_gray))
                    icCategories.setColorFilter(resources.getColor(R.color.text_color_gray))
                }
            })


            bottomSheet.setOnCitySelectedListener { selectedCity ->
                binding.apply {
                    textCategory.text = selectedCity
                    textCategory.setTextColor(
                        ContextCompat.getColor(
                            requireContext(), R.color.selected_color_in_search_fragment
                        )
                    )
                    icCategories.setColorFilter(
                        ContextCompat.getColor(
                            requireContext(), R.color.selected_color_in_search_fragment
                        )
                    )
                    icDownCategories.setColorFilter(
                        ContextCompat.getColor(
                            requireContext(), R.color.selected_color_in_search_fragment
                        )
                    )
                    btnCategories.setBackgroundResource(R.drawable.bg_selected_search_fragment)
                }

            }

            bottomSheet.show(childFragmentManager, "CustomBottomSheet")


        }

        binding.btnPrice.setOnClickListener {
            val bottomSheet = PriceFilterBottomSheet { minPrice, maxPrice, onDelete ->
                if (onDelete) {
                    resetPriceSelection()
                }
                else if (minPrice.isEmpty() && maxPrice.isEmpty()){
                    binding.btnPrice.setBackgroundResource(R.drawable.bg_unselected_in_search_fragment)
                }
                else {
                    updatePriceSelection(minPrice, maxPrice)
                }
            }

            bottomSheet.show(childFragmentManager, "PriceFilter")
        }



    }


    private fun initListeners() {
        binding.rvSearch.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                val visibleItemCount = layoutManager.childCount
                val totalItemCount = layoutManager.itemCount
                val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()

                if (!viewModel.getLoadingState()) {
                    Log.e("Pagination", "onScrolled: " + viewModel.houses.value.size)
                    if ((visibleItemCount + firstVisibleItemPosition) >= totalItemCount && firstVisibleItemPosition >= 0 && totalItemCount >= LIMIT_REGULAR) {
                        viewModel.getPageInfoDefault(viewModel.houses.value.size)
                    }

                }


            }
        })

        binding.inputSearch.apply {
            imeOptions = EditorInfo.IME_ACTION_SEARCH
            inputType = InputType.TYPE_CLASS_TEXT

            setOnEditorActionListener { _, actionId, event ->
                if (actionId == EditorInfo.IME_ACTION_SEARCH || (event?.keyCode == KeyEvent.KEYCODE_ENTER && event.action == KeyEvent.ACTION_DOWN)) {
                    val searchQuery = text.toString()
                    performSearch(searchQuery)
                    val imm =
                        context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                    imm.hideSoftInputFromWindow(windowToken, 0)

                    true
                } else {
                    false
                }
            }
        }


    }

    private fun performSearch(query: String) {
        viewModel.search(query)
    }

    private fun observeViewModel() {

        lifecycleScope.launch {
            viewModel.searchState.collectLatest {
                when (it) {
                    is ResponseBodyState.Error -> {
                        binding.progressBar.visibility = View.GONE
                    }

                    ResponseBodyState.Loading -> {
                        binding.progressBar.visibility = View.VISIBLE
                    }

                    is ResponseBodyState.SuccessList -> {
                        delay(200)
                        isLoading = false
                        binding.progressBar.visibility = View.GONE
                        setAdapter()
                    }

                    else -> {}
                }
            }
        }
        lifecycleScope.launch {
            viewModel.houses.collectLatest {
                adapter =
                    AdapterAdvertisements(viewModel.houses.value, viewModel, findNavController())
                binding.rvSearch.layoutManager =
                    LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
                binding.rvSearch.adapter = adapter
            }
        }
    }

    private fun setAdapter() {
        if (adapter == null) {
            adapter = AdapterAdvertisements(viewModel.houses.value, viewModel, findNavController())
            binding.rvSearch.layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            binding.rvSearch.adapter = adapter
        } else {
            if (viewModel.needToReinitialise()) {
                adapter =
                    AdapterAdvertisements(viewModel.houses.value, viewModel, findNavController())
                binding.rvSearch.layoutManager =
                    LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
                binding.rvSearch.adapter = adapter
                viewModel.setReinitialiseFalse()
            } else {
                adapter?.updateList()
            }
        }
    }


    private fun updatePriceButtonStyle() {
        val selectedColor =
            ContextCompat.getColor(requireContext(), R.color.selected_color_in_search_fragment)


        binding.apply {
            icPrice.setColorFilter(selectedColor)
            priceText.setTextColor(selectedColor)
            icPriceDown.setColorFilter(selectedColor)
            btnPrice.setBackgroundResource(R.drawable.bg_selected_search_fragment)
        }

    }


    private fun resetPriceSelection() {
        binding.apply {
            priceText.text = "Bahasy"
            priceText.setTextColor(resources.getColor(R.color.text_color_gray))
            icPriceDown.setColorFilter(resources.getColor(R.color.text_color_gray))
            btnPrice.setBackgroundResource(R.drawable.bg_unselected_in_search_fragment)
            icPrice.setColorFilter(ContextCompat.getColor(requireContext(), R.color.text_color_gray))
        }
    }

    /**
     * Функция для обновления фильтра цены
     */
    private fun updatePriceSelection(minPrice: String, maxPrice: String) {
        val priceText = when {
            minPrice.isNotEmpty() && maxPrice.isNotEmpty() -> "$minPrice - $maxPrice TMT"
            minPrice.isNotEmpty() -> "$minPrice + TMT"
            maxPrice.isNotEmpty() -> "0 - $maxPrice TMT"
            else -> "Bahasy"
        }

        binding.apply {
            this.priceText.text = priceText
            this.priceText.setTextColor(ContextCompat.getColor(requireContext(), R.color.selected_color_in_search_fragment))
            btnPrice.setBackgroundResource(R.drawable.bg_selected_search_fragment)
            icPriceDown.setColorFilter(ContextCompat.getColor(requireContext(), R.color.selected_color_in_search_fragment))
            icPrice.setColorFilter(ContextCompat.getColor(requireContext(), R.color.selected_color_in_search_fragment))
        }

    }


}

