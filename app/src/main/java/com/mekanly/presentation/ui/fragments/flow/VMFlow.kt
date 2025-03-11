package com.mekanly.presentation.ui.fragments.flow

import androidx.lifecycle.ViewModel
import com.mekanly.data.dataModels.DataBanner
import com.mekanly.data.responseBody.DataGlobalOptions
import com.mekanly.data.responseBody.ResponseBodyState
import com.mekanly.domain.useCase.UseCaseGlobalOptions
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class VMFlow:ViewModel() {
    private val _globalState= MutableStateFlow(DataGlobalOptions())
    val globalState: StateFlow<DataGlobalOptions> = _globalState.asStateFlow()

    private val useCase by lazy {
        UseCaseGlobalOptions()
    }

    init {
        getGlobalOptions()
    }

    private fun getGlobalOptions() {
        useCase.execute{
            when(it){
                is ResponseBodyState.Error -> {

                }
                is ResponseBodyState.Success -> {
                _globalState.value = it.dataResponse as DataGlobalOptions
                }
                else -> {}
            }
        }
    }
}