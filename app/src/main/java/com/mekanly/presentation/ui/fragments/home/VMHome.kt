package com.mekanly.presentation.ui.fragments.home

import androidx.lifecycle.ViewModel
import com.mekanly.data.responseBody.ResponseBodyState
import com.mekanly.domain.useCase.GetHousesUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class VMHome:ViewModel() {

    private val _homeState = MutableStateFlow<ResponseBodyState>(ResponseBodyState.Loading)
    val homeState: StateFlow<ResponseBodyState> = _homeState.asStateFlow()

    private val useCase by lazy {
        GetHousesUseCase()
    }

    init {
        getHouses()
    }

    private fun getHouses() {
        useCase.execute {
            _homeState.value = it
        }
    }
}