package com.mekanly.presentation.ui.fragments.search.viewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import com.mekanly.data.models.House
import com.mekanly.data.models.Location
import com.mekanly.data.models.PriceRange
import com.mekanly.data.models.HouseCategory
import com.mekanly.domain.model.ResponseBodyState
import com.mekanly.domain.useCase.GetPaginatedHousesUseCase
import com.mekanly.domain.useCase.SearchPaginatedHousesUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class VMSearch : ViewModel() {


    companion object {
        const val FILTER_TYPE_DEFAULT = 0
        const val FILTER_TYPE_LOCATION = 1
        const val FILTER_TYPE_CATEGORY = 2
        const val FILTER_TYPE_PRICE = 3
    }

    private val _houses = MutableStateFlow<MutableList<House>>(mutableListOf())
    val houses: StateFlow<List<House>> = _houses.asStateFlow()

    private val _searchState = MutableStateFlow<ResponseBodyState>(ResponseBodyState.Loading)
    val searchState: StateFlow<ResponseBodyState> = _searchState.asStateFlow()

    private val _isLoading = MutableStateFlow(true)
    private val _needToReinitialiseAdapter = MutableStateFlow(false)

    private val _currentSelectedFilter = MutableStateFlow<Int>(FILTER_TYPE_DEFAULT)
    val currentSelectedFilter: StateFlow<Int> = _currentSelectedFilter.asStateFlow()


    private val getHousesUseCase by lazy { GetPaginatedHousesUseCase() }
    private val searchUseCase by lazy { SearchPaginatedHousesUseCase() }

    init {
        getPageInfoDefault(0)
    }

    fun getLoadingState(): Boolean {
        return _isLoading.value
    }

    fun needToReinitialise(): Boolean {
        return _needToReinitialiseAdapter.value
    }

    fun setReinitialiseFalse(){
        _needToReinitialiseAdapter.value = false
    }

    fun getPageInfoDefault(
        size: Int, location: Location? = null, category: HouseCategory? = null, priceRange: PriceRange?=null
    ) {
        _isLoading.value = true
        if (location != null) {
            getHousesUseCase.execute(size.toLong(), location = location) { result ->
                _searchState.value = result
                when (result) {
                    is ResponseBodyState.SuccessList -> {
                        _houses.value.clear()
                        _isLoading.value = false
                        val data = result.dataResponse as List<House>
                        _houses.value.addAll(data)
                    }

                    is ResponseBodyState.Error -> {
                        _isLoading.value = false
                    }

                    else -> {}
                }
            }
        } else if (category != null) {
            getHousesUseCase.execute(size.toLong(), category = category) { result ->
                _searchState.value = result
                when (result) {
                    is ResponseBodyState.SuccessList -> {
                        _houses.value.clear()
                        _isLoading.value = false
                        val data = result.dataResponse as List<House>
                        _houses.value.addAll(data)
                    }

                    is ResponseBodyState.Error -> {
                        _isLoading.value = false
                    }

                    else -> {}
                }
            }
        } else if (priceRange!=null){
            Log.e("PRICE_FILTER", "getPageInfoDefault: ", )
            getHousesUseCase.execute(size.toLong(), priceRange = priceRange) { result ->
                _searchState.value = result
                when (result) {
                    is ResponseBodyState.SuccessList -> {
                        _houses.value.clear()
                        _isLoading.value = false
                        val data = result.dataResponse as List<House>
                        _houses.value.addAll(data)
                    }

                    is ResponseBodyState.Error -> {
                        _isLoading.value = false
                    }

                    else -> {}
                }
            }
        }else {
            getHousesUseCase.execute(size.toLong()) { result ->
                _searchState.value = result
                when (result) {
                    is ResponseBodyState.SuccessList -> {
                        _isLoading.value = false
                        val data = result.dataResponse as List<House>
                        _houses.value.addAll(data)
                    }

                    is ResponseBodyState.Error -> {
                        _isLoading.value = false
                    }

                    else -> {}
                }
            }
        }

    }

    fun search(query: String) {
        _needToReinitialiseAdapter.value = true
        _isLoading.value = true
        searchUseCase.search(query) { result ->
            _searchState.value = result
            when (result) {
                is ResponseBodyState.SuccessList -> {
                    _isLoading.value = false
                    _houses.value.clear()
                    val houses = result.dataResponse as List<House>
                    _houses.value.addAll(houses)
                }
                is ResponseBodyState.Error -> {
                    _isLoading.value = false
                }
                else -> {}
            }
        }
    }

    fun updateFilterType(filterTypeCategory: Int) {
        _currentSelectedFilter.value = filterTypeCategory
    }
}
