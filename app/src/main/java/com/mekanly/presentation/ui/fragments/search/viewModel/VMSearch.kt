package com.mekanly.presentation.ui.fragments.search.viewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import com.mekanly.data.dataModels.DataHouse
import com.mekanly.data.responseBody.ResponseBodyState
import com.mekanly.domain.useCase.UseCasePaginatedHouses
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class VMSearch : ViewModel() {

    private val _houses = MutableStateFlow(emptyList<DataHouse>().toMutableList())
    val houses: StateFlow<MutableList<DataHouse>> = _houses.asStateFlow()

    private val _searchState = MutableStateFlow<ResponseBodyState>(ResponseBodyState.Loading)

    val searchState: StateFlow<ResponseBodyState> = _searchState.asStateFlow()

    private val _isLoading = MutableStateFlow<Boolean>(true)

    private val useCase by lazy {
        UseCasePaginatedHouses()
    }

    init {
        getPageInfoDefault(0)
    }

    fun getLoadingState():Boolean{
        return _isLoading.value
    }

    fun getPageInfoDefault(size: Int) {
        _isLoading.value = true
        useCase.execute(size.toLong()) {
            _searchState.value = it
            when(it){
                is ResponseBodyState.SuccessList -> {
                    _isLoading.value = false
                    it.dataResponse as List<DataHouse>
                    _houses.value.addAll(it.dataResponse)
                }
                is ResponseBodyState.Error->{
                    _isLoading.value = false
                }
                else -> {}
            }
        }
    }
}