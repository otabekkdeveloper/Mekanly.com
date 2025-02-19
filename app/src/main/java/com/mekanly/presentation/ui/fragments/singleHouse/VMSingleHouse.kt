package com.mekanly.presentation.ui.fragments.singleHouse

import androidx.lifecycle.ViewModel
import com.mekanly.data.responseBody.ResponseBodyState
import com.mekanly.domain.useCase.UseCaseHouseDetails
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class VMSingleHouse : ViewModel() {
    private val _singleHouseState = MutableStateFlow<ResponseBodyState>(ResponseBodyState.Loading)
    val singleHouseState: StateFlow<ResponseBodyState> = _singleHouseState.asStateFlow()
    private val useCase by lazy {
        UseCaseHouseDetails()
    }
    private fun getSingleHouseDetails(houseId: Long)
    {
        useCase.execute(houseId) {
            _singleHouseState.value = it
        }
    }
}