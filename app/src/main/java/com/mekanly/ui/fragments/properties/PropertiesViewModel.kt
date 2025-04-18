package com.mekanly.ui.fragments.properties

import android.util.Log
import androidx.lifecycle.ViewModel
import com.mekanly.data.models.House
import com.mekanly.domain.model.ResponseBodyState
import com.mekanly.domain.useCase.GetUserPropertiesUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class PropertiesViewModel : ViewModel() {

    private val _uiState = MutableStateFlow<ResponseBodyState>(ResponseBodyState.Loading)
    val uiState: StateFlow<ResponseBodyState> = _uiState.asStateFlow()


    private val _houses = MutableStateFlow<List<House>>(emptyList())
    val houses: StateFlow<List<House>> = _houses.asStateFlow()

    private val _isLoading = MutableStateFlow(true)
    private val _needToReinitialiseAdapter = MutableStateFlow(false)

    private val getUserHousesUseCase by lazy { GetUserPropertiesUseCase() }

    init {
        getHouses()
    }

    fun getLoadingState(): Boolean {
        return _isLoading.value
    }

    fun needToReinitialise(): Boolean {
        return _needToReinitialiseAdapter.value
    }

    fun setReinitialiseFalse() {
        _needToReinitialiseAdapter.value = false
    }

    fun getHouses() {
        getUserHousesUseCase.execute() { result ->
            when (result) {
                is ResponseBodyState.SuccessList -> {
                    _isLoading.value = false
                    val data = result.dataResponse as List<House>
                    _houses.value = data
                }
                is ResponseBodyState.Error -> {
                    _isLoading.value = false
                }
                else -> {}
            }
            _uiState.value = result
        }
    }
}