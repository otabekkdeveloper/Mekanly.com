package com.mekanly.ui.fragments.favorite

import androidx.lifecycle.ViewModel
import com.mekanly.data.models.House
import com.mekanly.domain.model.ResponseBodyState
import com.mekanly.domain.useCase.GetUserPropertiesUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow


class FavoritesViewModel : ViewModel() {

    private val _uiState = MutableStateFlow<ResponseBodyState>(ResponseBodyState.Loading)
    val uiState: StateFlow<ResponseBodyState> = _uiState.asStateFlow()

    private val _houses = MutableStateFlow<List<House>>(emptyList())
    val houses: StateFlow<List<House>> = _houses.asStateFlow()

    private val getUserHousesUseCase by lazy { GetUserPropertiesUseCase() }

    init {
        getHouses()
    }

    fun getHouses() {
        getUserHousesUseCase.execute() { result ->
            when (result) {
                is ResponseBodyState.SuccessList -> {
                    val data = result.dataResponse as List<House>
                    _houses.value = data
                }
                is ResponseBodyState.Error -> {
                }
                else -> {}
            }
            _uiState.value = result
        }
    }


}