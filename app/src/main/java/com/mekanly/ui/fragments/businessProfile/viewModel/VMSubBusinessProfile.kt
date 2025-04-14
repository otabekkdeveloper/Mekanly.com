package com.mekanly.presentation.ui.fragments.businessProfile.viewModel

import androidx.lifecycle.ViewModel
import com.mekanly.data.models.BusinessProfile
import com.mekanly.data.models.BusinessCategory
import com.mekanly.domain.model.ResponseBodyState
import com.mekanly.domain.useCase.GetBusinessProfilesUseCase
import com.mekanly.domain.useCase.GetSimilarBusinessProfilesUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

sealed class FragmentSubBusinessProfileState() {
    data class SuccessBusinessProfiles(val dataResponse: List<BusinessProfile>) :
        FragmentSubBusinessProfileState()

    data class SuccessCategories(val dataResponse: List<BusinessCategory>) :
        FragmentSubBusinessProfileState()

    data object Loading : FragmentSubBusinessProfileState()
    data object Initial : FragmentSubBusinessProfileState()
    data class Error(val error: Any) : FragmentSubBusinessProfileState()
}

class VMSubBusinessProfile : ViewModel() {

    private val _fragmentState =
        MutableStateFlow<FragmentSubBusinessProfileState>(FragmentSubBusinessProfileState.Loading)
    val fragmentState: StateFlow<FragmentSubBusinessProfileState> = _fragmentState.asStateFlow()

    private val getBusinessProfilesUseCase by lazy {
        GetBusinessProfilesUseCase()
    }

    private val getSimilarBusinessProfilesUseCase by lazy {
        GetSimilarBusinessProfilesUseCase()
    }



    fun getSimilarBusinessProfiles(id: Long) {
        getSimilarBusinessProfilesUseCase.invoke(id) {
        when(it){
            is ResponseBodyState.Error -> {
                _fragmentState.value = FragmentSubBusinessProfileState.Error(it.error)
            }
            ResponseBodyState.Loading ->{
                _fragmentState.value = FragmentSubBusinessProfileState.Loading
            }
            is ResponseBodyState.SuccessList -> {
                _fragmentState.value = FragmentSubBusinessProfileState.SuccessBusinessProfiles(it.dataResponse as List<BusinessProfile>)
            }
            else -> {}
        }
        }
    }

    fun getSimilarCategories(categoryId: Int) {

    }
}