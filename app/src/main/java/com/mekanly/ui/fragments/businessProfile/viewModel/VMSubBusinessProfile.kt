package com.mekanly.presentation.ui.fragments.businessProfile.viewModel

import androidx.lifecycle.ViewModel
import com.mekanly.data.models.BusinessProfile
import com.mekanly.data.models.BusinessCategory
import com.mekanly.data.models.BusinessSubCategory
import com.mekanly.domain.model.ResponseBodyState
import com.mekanly.domain.useCase.GetBusinessProfilesUseCase
import com.mekanly.domain.useCase.GetSimilarBusinessCategoriesUseCase
import com.mekanly.domain.useCase.GetSimilarBusinessProfilesUseCase
import com.mekanly.domain.useCase.UseCaseGetCategoryProducts
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.asStateFlow


sealed class FragmentSubBusinessProfileState() {
    data class SuccessBusinessProfiles(val dataResponse: List<BusinessProfile>) :
        FragmentSubBusinessProfileState()

    data class SuccessCategories(val dataResponse: List<BusinessCategory>) :
        FragmentSubBusinessProfileState()

    data class SuccessSimilarCategories(val dataResponse: List<BusinessCategory>) :
        FragmentSubBusinessProfileState()

    data object Loading : FragmentSubBusinessProfileState()
    data object Initial : FragmentSubBusinessProfileState()
    data class Error(val error: Any) : FragmentSubBusinessProfileState()
}

class VMSubBusinessProfile : ViewModel() {

    private val _fragmentState =
        MutableStateFlow<FragmentSubBusinessProfileState>(FragmentSubBusinessProfileState.Loading)
    val fragmentState: StateFlow<FragmentSubBusinessProfileState> = _fragmentState.asStateFlow()

    private val _categoryProductsState =
        MutableStateFlow<ResponseBodyState>(ResponseBodyState.Loading)
    val categoryProductsState get() = _categoryProductsState.asStateFlow()

    private val getBusinessProfilesUseCase by lazy {
        GetBusinessProfilesUseCase()
    }

    private val getSimilarBusinessProfilesUseCase by lazy {
        GetSimilarBusinessProfilesUseCase()
    }

    private val getSimilarBusinessCategoriesUseCase by lazy {
        GetSimilarBusinessCategoriesUseCase()
    }

    private val getCategoryProductsUseCase by lazy {
        UseCaseGetCategoryProducts()
    }


    fun getSimilarBusinessProfiles(id: Long) {
        getSimilarBusinessProfilesUseCase.invoke(id) {
            when (it) {
                is ResponseBodyState.Error -> {
                    _fragmentState.value = FragmentSubBusinessProfileState.Error(it.error)
                }

                ResponseBodyState.Loading -> {
                    _fragmentState.value = FragmentSubBusinessProfileState.Loading
                }

                is ResponseBodyState.SuccessList -> {
                    _fragmentState.value =
                        FragmentSubBusinessProfileState.SuccessBusinessProfiles(it.dataResponse as List<BusinessProfile>)
                }

                else -> {}
            }
        }
    }

    fun getSimilarCategories(categoryId: Long) {

        getSimilarBusinessCategoriesUseCase.invoke(categoryId) {

            when (it) {
                is ResponseBodyState.Error -> {
                    _fragmentState.value = FragmentSubBusinessProfileState.Error(it.error)
                }

                ResponseBodyState.Loading -> {
                    _fragmentState.value = FragmentSubBusinessProfileState.Loading
                }

                is ResponseBodyState.SuccessList -> {
                    _fragmentState.value =
                        FragmentSubBusinessProfileState.SuccessSimilarCategories(it.dataResponse as List<BusinessCategory>)
                }

                else -> {}


            }
        }

    }

    fun getCategoryProducts(categoryId: Int) {
        getCategoryProductsUseCase.execute(categoryId) {
            _categoryProductsState.value = it
        }
    }
}