package com.mekanly.presentation.ui.fragments.search.viewModel

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

    private val useCase by lazy {
        UseCasePaginatedHouses()
    }

    init {
        getPageInfoDefault()
    }

    fun getPageInfoDefault() {
        useCase.execute(start = _houses.value.size.toLong()) {
            when(it){
                is ResponseBodyState.Error -> {

                }
                ResponseBodyState.Loading -> {

                }
                is ResponseBodyState.Success -> {

                }

                is ResponseBodyState.SuccessList -> {
                    it.dataResponse as List<DataHouse>
                    _houses.value.addAll(it.dataResponse)
                }
            }
        }
    }
}