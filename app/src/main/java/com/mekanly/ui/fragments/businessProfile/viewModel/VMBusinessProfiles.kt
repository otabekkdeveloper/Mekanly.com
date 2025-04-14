package com.mekanly.presentation.ui.fragments.businessProfile.viewModel

import androidx.lifecycle.ViewModel
import com.mekanly.data.models.BusinessProfile
import com.mekanly.data.models.BusinessCategory
import com.mekanly.data.responseBody.ResponseBodyState
import com.mekanly.domain.useCase.GetBusinessCategoriesUseCase
import com.mekanly.domain.useCase.UseCaseBusinessProfiles
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
sealed class FragmentBusinessProfileState(){
    data class SuccessBusinessProfiles(val dataResponse:List<BusinessProfile>):FragmentBusinessProfileState()
    data class SuccessCategories(val dataResponse:List<BusinessCategory>):FragmentBusinessProfileState()
    data object Loading:FragmentBusinessProfileState()
    data object Initial:FragmentBusinessProfileState()
    data class Error(val error:Any):FragmentBusinessProfileState()
}
class VMBusinessProfiles:ViewModel() {

    private val _fragmentState = MutableStateFlow<FragmentBusinessProfileState>(FragmentBusinessProfileState.Loading)
    val fragmentState: StateFlow<FragmentBusinessProfileState> = _fragmentState.asStateFlow()

    private val _isLoading = MutableStateFlow(true)

    private val _businessProfiles = MutableStateFlow<MutableList<BusinessProfile>>(mutableListOf())
    val businessProfiles: StateFlow<List<BusinessProfile>> = _businessProfiles.asStateFlow()

    private val useCase by lazy {
        GetBusinessCategoriesUseCase()
    }

    private val useCaseBusinessProfiles by lazy {
        UseCaseBusinessProfiles()
    }
    init {
        getBusinessProfileCategories()
        getPageInfoDefault(0)
    }

    private fun getBusinessProfileCategories() {
       useCase.execute {
          when(it){
              is ResponseBodyState.Error -> {
                  _fragmentState.value = FragmentBusinessProfileState.Error(it.error)
              }
              ResponseBodyState.Loading ->{
                  _fragmentState.value = FragmentBusinessProfileState.Loading
              }
              is ResponseBodyState.SuccessList ->{
                  _fragmentState.value = FragmentBusinessProfileState.SuccessCategories(it.dataResponse as List<BusinessCategory>)
              }

              else -> {}
          }
       }
    }


    fun getLoadingState(): Boolean {
        return _isLoading.value
    }


    fun getPageInfoDefault(size: Int) {
        _isLoading.value = true
        useCaseBusinessProfiles.execute(size.toLong()) { result ->

            when (result) {
                is ResponseBodyState.SuccessList -> {
                    _isLoading.value = false
                    val data = result.dataResponse as List<BusinessProfile>
                    _businessProfiles.value.addAll(data)
                    _fragmentState.value = FragmentBusinessProfileState.SuccessBusinessProfiles(_businessProfiles.value.toList())
                }
                is ResponseBodyState.Error -> {
                    _isLoading.value = false
                    _fragmentState.value = FragmentBusinessProfileState.Error(result.error)
                }
                is ResponseBodyState.Loading->{
                    _fragmentState.value = FragmentBusinessProfileState.Loading
                }
                else -> {}
            }
        }
    }

    fun setInitialState(){
        _fragmentState.value = FragmentBusinessProfileState.Initial
    }
}