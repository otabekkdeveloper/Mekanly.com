package com.mekanly.presentation.ui.fragments.businessProfile.viewModel

import androidx.lifecycle.ViewModel
import com.mekanly.data.responseBody.ResponseBodyState
import com.mekanly.domain.useCase.UseCaseBusinessProfileCategories
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class VMBusinessProfiles:ViewModel() {

    private val _fragmentState = MutableStateFlow<ResponseBodyState>(ResponseBodyState.Loading)
    val fragmentState: StateFlow<ResponseBodyState> = _fragmentState.asStateFlow()

    private val useCase by lazy {
        UseCaseBusinessProfileCategories()
    }
    init {
        getBusinessProfileCategories()
    }

    private fun getBusinessProfileCategories() {
       useCase.execute {
          _fragmentState.value = it
       }
    }
}