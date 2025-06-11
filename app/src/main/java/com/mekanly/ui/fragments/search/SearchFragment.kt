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
            PreferencesHelper.getGlobalOptions()?.let { it -> openPriceSelector(it)}
        }


    }


    // Модифицируйте метод initListeners() в вашем SearchFragment так:

    private fun initListeners() {
        binding.rvSearch.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                val visibleItemCount = layoutManager.childCount
                val totalItemCount = layoutManager.itemCount
                val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()

                // dy > 20 — прокрутили вниз, dy < -20 — прокрутили вверх
                if (dy > 20) {
                    // Показать кнопку
                    binding.arrotToTop.visibility = View.VISIBLE
                } else if (dy < -20) {
                    // Скрыть кнопку
                    binding.arrotToTop.visibility = View.GONE
                }
                binding.arrotToTop.setOnClickListener {
                    recyclerView.smoothScrollToPosition(0)
                }



                if (!viewModel.getLoadingState()) {
                    Log.e("Pagination", "onScrolled: " + viewModel.houses.value.size)
                    if ((visibleItemCount + firstVisibleItemPosition) >= totalItemCount && firstVisibleItemPosition >= 0 && totalItemCount >= LIMIT_REGULAR) {
                        // Используем loadMoreSearchResults вместо getHouses
                        viewModel.loadMoreSearchResults()
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
                    viewModel.searchHouses()
                    val imm =
                        context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                    imm.hideSoftInputFromWindow(windowToken, 0)

                    true
                } else {
                    false
                }
            }
        }

        // Добавьте обработчик для кнопки очистки поиска, если она есть в вашем макете
//        binding.clearSearchBtn?.setOnClickListener {
//            binding.inputSearch.text?.clear()
//            viewModel.clearSearch()
//        }
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
                adapter = AdapterAdvertisements(viewModel.houses.value, viewModel, findNavController())
                binding.rvSearch.layoutManager =
                    LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
                binding.rvSearch.adapter = adapter
            }
        }

        lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {


                launch {
                    viewModel.price.collect { price ->
                        updatePriceUI(price)
                        viewModel.getHouses()
                        // УБРАЛИ: viewModel.getHouses(0)
                    }
                }

                // Слежение за изменением локации - УБИРАЕМ автоматический запрос
                launch {
                    viewModel.selectedLocation.collect { location ->
                        Log.d("LocationUpdate", "Получена новая локация: ${location?.isSelectedByFiler}")
                        updateLocationUI(location)


                        // УБРАЛИ: viewModel.getHouses(0)
                    }
                }

                // Убираем автоматические запросы и для других фильтров
                launch {
                    viewModel.selectedCategory.collect { category ->
                        updateCategoryUI(category)
                        // УБРАЛИ: viewModel.getHouses(0)
                    }
                }
            }
        }
    }


    private fun updateLocationUI(location: Location?) {
        val isSelected = location != null
        Log.e("LOCATION_TEST", "observeViewModel: "+location.toString() )
        binding.apply {
            nameLocations.text = when {


                location?.parentId != null  -> {
                    "${location.parentName}, ${location.name}"
                }

                location!=null->{
                    location.name
                }


                else -> {
                    getString(R.string.location)
                }
            }

            val textColor = ContextCompat.getColor(
                requireContext(), if (isSelected) R.color.selected_color_in_search_fragment
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
                requireContext(), if (isSelected) R.color.selected_color_in_search_fragment
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
                requireContext(), if (isSelected) R.color.selected_color_in_search_fragment
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
        if (globalOptions.houseCategories.isNotEmpty()) {
            val houseCategories = globalOptions.houseCategories
            val bottomSheet = SectionSelectionBottomSheet(houseCategories, onDelete = {
                viewModel.setSelectedCategory(null)
                binding.textCategory.text = getString(R.string.category)
                // Делаем запрос ТОЛЬКО после удаления категории
                viewModel.getHouses(0)
            }) { selectedCategory ->
                viewModel.setSelectedCategory(selectedCategory)
                updateCategoryUI(selectedCategory)
                Log.e("selected", "openCategorySelector: $selectedCategory")
                // Делаем запрос ТОЛЬКО после выбора категории
                viewModel.getHouses(0)
            }

            bottomSheet.show(childFragmentManager, "CategoryBottomSheet")
        }
    }

    private fun openLocationSelector(globalOptions: DataGlobalOptions) {
        if (globalOptions.locations.isNotEmpty()) {
            val cities = globalOptions.locations.filter { it.parentId == null }

            LocationBottomSheet.showWithoutChildren(
                parent = this,
                cities = cities,
                onDelete = {
                    viewModel.setSelectedLocation(null)
                    binding.nameLocations.text = getString(R.string.location)
                    // Делаем запрос ТОЛЬКО после удаления локации
                    viewModel.getHouses(0)
                },
                onCitySelected = { selectedCity ->
                    viewModel.setSelectedLocation(selectedCity)
                    updateLocationUI(selectedCity)
                    Log.e("selected", "openLocationSelector: $selectedCity")
                    // Делаем запрос ТОЛЬКО после выбора локации
                    viewModel.getHouses(0)
                }
            )
        }
    }

    private fun openPriceSelector(priceRange: DataGlobalOptions) {
        val bottomSheet = PriceFilterBottomSheet { minPrice, maxPrice ->
            val minValue = parseFormattedPrice(minPrice)
            val maxValue = parseFormattedPrice(maxPrice)

            val newPrice = PriceRange(minValue, maxValue)
            viewModel.setPrice(newPrice)
            updatePriceUI(newPrice)
            // Делаем запрос ТОЛЬКО после выбора цены
            viewModel.getHouses(0)
        }

        bottomSheet.show(childFragmentManager, "PriceFilterBottomSheet")
    }

    private fun parseFormattedPrice(formattedPrice: String): Int? {
        return try {
            when {
                formattedPrice.isEmpty() -> null
                formattedPrice.contains("mln") -> {
                    val value = formattedPrice.replace("mln", "").trim().toDouble()
                    (value * 1_000_000).toInt()
                }
                formattedPrice.contains("müň") -> {
                    val value = formattedPrice.replace("müň", "").trim().toDouble()
                    (value * 1_000).toInt()
                }
                else -> formattedPrice.toInt()
            }
        } catch (e: NumberFormatException) {
            null
        }
    }

}

