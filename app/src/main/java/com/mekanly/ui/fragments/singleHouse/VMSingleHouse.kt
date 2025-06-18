package com.mekanly.presentation.ui.fragments.singleHouse

import androidx.lifecycle.ViewModel
import com.mekanly.domain.model.ResponseBodyState
import com.mekanly.domain.useCase.GetHouseDetailsUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class VMSingleHouse : ViewModel() {
    private val _singleHouseState = MutableStateFlow<ResponseBodyState>(ResponseBodyState.Loading)
    val singleHouseState: StateFlow<ResponseBodyState> = _singleHouseState.asStateFlow()
    private val useCase by lazy {
        GetHouseDetailsUseCase()
    }


    fun getHouseDetails(houseId: Long)
    {
        useCase.execute(houseId) {
            _singleHouseState.value = it
        }
    }

}