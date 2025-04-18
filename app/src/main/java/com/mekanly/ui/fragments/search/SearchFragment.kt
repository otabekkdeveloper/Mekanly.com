package com.mekanly.ui.fragments.search

import LocationBottomSheet
import android.content.Context
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
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mekanly.R
import com.mekanly.data.models.DataGlobalOptions
import com.mekanly.data.models.HouseCategory
import com.mekanly.data.models.Location
import com.mekanly.data.models.PriceRange
import com.mekanly.data.repository.HousesRepository.Companion.LIMIT_REGULAR
import com.mekanly.domain.model.ResponseBodyState
import com.mekanly.databinding.FragmentSearchBinding
import com.mekanly.helpers.PreferencesHelper
import com.mekanly.presentation.ui.adapters.AdapterAdvertisements
import com.mekanly.presentation.ui.bottomSheet.PriceFilterBottomSheet
import com.mekanly.ui.bottomSheet.SectionSelectionBottomSheet
import com.mekanly.ui.fragments.search.viewModel.VMSearch
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


@Suppress("NAME_SHADOWING")
class SearchFragment : Fragment() {

    private lateinit var binding: FragmentSearchBinding

    private val viewModel: VMSearch by activityViewModels()

    private var isLoading: Boolean = false

    private var adapter: AdapterAdvertisements? = null


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

        binding.btnFilter.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_filterFragment)
        }

        binding.btnLocations.setOnClickListener {
            PreferencesHelper.getGlobalOptions()?.let { it -> openLocationSelector(it) }
        }

        binding.btnCategories.setOnClickListener {
            PreferencesHelper.getGlobalOptions()?.let { it -> openCategorySelector(it) }
        }

        binding.btnPrice.setOnClickListener {
            val bottomSheet = PriceFilterBottomSheet { minPrice, maxPrice ->
                viewModel.setPrice(PriceRange(minPrice.toIntOrNull(), maxPrice.toIntOrNull()))
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
                        viewModel.getHouses(viewModel.houses.value.size)
                    }

                }


            }
        })

        binding.inputSearch.apply {
            imeOptions = EditorInfo.IME_ACTION_SEARCH
            inputType = InputType.TYPE_CLASS_TEXT

            setOnEditorActionListener { _, actionId, event ->
                if (actionId == EditorInfo.IME_ACTION_SEARCH || (event?.keyCode == KeyEvent.KEYCODE_ENTER && event.action == KeyEvent.ACTION_DOWN)) {
                    val searchQuery = text?.trim().toString()
                    viewModel.setSearch(searchQuery)
                    viewModel.getHouses()
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

        lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {

                launch {
                    viewModel.selectedLocation.collect { location ->
                        updateLocationUI(location)
                        viewModel.getHouses()
                    }
                }

                launch {
                    viewModel.selectedCategory.collect { category ->
                        updateCategoryUI(category)
                        viewModel.getHouses()
                    }
                }
                launch {
                    viewModel.price.collect { price ->
                        updatePriceUI(price)
                        viewModel.getHouses()
                    }
                }
            }
        }
    }


    private fun updateLocationUI(location: Location?) {
        val isSelected = location != null
        binding.apply {
            nameLocations.text = location?.name ?: getString(R.string.location)

            val textColor = ContextCompat.getColor(
                requireContext(),
                if (isSelected) R.color.selected_color_in_search_fragment
                else R.color.text_color_gray
            )
            val btnBackground = if (isSelected) {
                R.drawable.bg_selected_search_fragment
            } else {
                R.drawable.bg_unselected_in_search_fragment
            }

            nameLocations.setTextColor(textColor)
            btnLocations.setBackgroundResource(btnBackground)
            icDown.setColorFilter(textColor)
            icLocation.setColorFilter(textColor)
        }
    }

    private fun updateCategoryUI(category: HouseCategory?) {
        val isSelected = category != null
        binding.apply {
            textCategory.text = category?.name ?: getString(R.string.category)

            val textColor = ContextCompat.getColor(
                requireContext(),
                if (isSelected) R.color.selected_color_in_search_fragment
                else R.color.text_color_gray
            )
            val btnBackground = if (isSelected) {
                R.drawable.bg_selected_search_fragment
            } else {
                R.drawable.bg_unselected_in_search_fragment
            }

            textCategory.setTextColor(textColor)
            btnCategories.setBackgroundResource(btnBackground)
            icDownCategories.setColorFilter(textColor)
            icCategories.setColorFilter(textColor)
        }
    }

    private fun updatePriceUI(priceFilter: PriceRange) {
        binding.apply {
            val isSelected = priceFilter.max != null || priceFilter.min != null

            val priceText = when {
                priceFilter.max == null && priceFilter.min == null -> getString(R.string.bahasy)
                priceFilter.max != null && priceFilter.min != null -> "${priceFilter.min} - ${priceFilter.max} TMT"
                priceFilter.min != null -> "${priceFilter.min} + TMT"
                priceFilter.max != null -> "0 - ${priceFilter.max}TMT"
                else -> getString(R.string.bahasy)
            }

            val textColor = ContextCompat.getColor(
                requireContext(),
                if (isSelected) R.color.selected_color_in_search_fragment
                else R.color.text_color_gray
            )
            val btnBackground = if (isSelected) {
                R.drawable.bg_selected_search_fragment
            } else {
                R.drawable.bg_unselected_in_search_fragment
            }

            this.priceText.text = priceText
            this.priceText.setTextColor(textColor)
            btnPrice.setBackgroundResource(btnBackground)
            icPriceDown.setColorFilter(textColor)
            icPrice.setColorFilter(textColor)
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

    private fun openCategorySelector(globalOptions: DataGlobalOptions) {
        val houseCategories = globalOptions.houseCategories
        val bottomSheet = SectionSelectionBottomSheet(
            houseCategories, onDelete = {
                viewModel.setSelectedCategory(null)
                binding.textCategory.text = getString(R.string.category)
            }
        )

        bottomSheet.setOnCategorySelectedListener { category ->
            viewModel.setSelectedCategory(category)
        }

        bottomSheet.show(childFragmentManager, "CustomBottomSheet")
    }

    private fun openLocationSelector(globalOptions: DataGlobalOptions) {
        if (globalOptions.locations.isNotEmpty()) {
            val cities = globalOptions.locations
            val bottomSheet = LocationBottomSheet(
                cities, onDelete = {
                    viewModel.setSelectedLocation(null)
                    binding.nameLocations.text = getString(R.string.location)
                }
            ) { selectedCity ->
                viewModel.setSelectedLocation(selectedCity)
            }
            bottomSheet.show(childFragmentManager, "LocationBottomSheet")
        }
    }

}

