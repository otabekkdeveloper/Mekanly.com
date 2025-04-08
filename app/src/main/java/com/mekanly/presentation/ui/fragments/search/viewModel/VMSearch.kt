package com.mekanly.presentation.ui.fragments.search.viewModel

import androidx.lifecycle.ViewModel
import com.mekanly.data.dataModels.DataHouse
import com.mekanly.data.dataModels.DataLocation
import com.mekanly.data.responseBody.ResponseBodyState
import com.mekanly.domain.useCase.UseCasePaginatedHouses
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class VMSearch : ViewModel() {

    private val _houses = MutableStateFlow<MutableList<DataHouse>>(mutableListOf())
    val houses: StateFlow<List<DataHouse>> = _houses.asStateFlow()

    private val _searchState = MutableStateFlow<ResponseBodyState>(ResponseBodyState.Loading)
    val searchState: StateFlow<ResponseBodyState> = _searchState.asStateFlow()

    private val _isLoading = MutableStateFlow(true)
    private val _needToReinitialiseAdapter = MutableStateFlow(false)

    private val useCase by lazy {
        UseCasePaginatedHouses()
    }

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

    fun getPageInfoDefault(size: Int, location: DataLocation? = null) {
        _isLoading.value = true
        if (location != null) {
            useCase.execute(size.toLong(), location) { result ->
                _searchState.value = result
                when (result) {
                    is ResponseBodyState.SuccessList -> {
                        _houses.value.clear()
                        _isLoading.value = false
                        val data = result.dataResponse as List<DataHouse>
                        _houses.value.addAll(data)
                    }

                    is ResponseBodyState.Error -> {
                        _isLoading.value = false
                    }

                    else -> {}
                }
            }
        } else {
            useCase.execute(size.toLong()) { result ->
                _searchState.value = result
                when (result) {
                    is ResponseBodyState.SuccessList -> {
                        _isLoading.value = false
                        val data = result.dataResponse as List<DataHouse>
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
        useCase.search(query) { result ->
            _searchState.value = result
            when (result) {
                is ResponseBodyState.SuccessList -> {
                    _isLoading.value = false
                    _houses.value.clear()
                    val houses = result.dataResponse as List<DataHouse>
                    _houses.value.addAll(houses)
                }
                is ResponseBodyState.Error -> {
                    _isLoading.value = false
                }
                else -> {}
            }
        }
    }
}
