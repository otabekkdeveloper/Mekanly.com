package com.mekanly.presentation.ui.fragments.businessProfile.viewModel

import androidx.lifecycle.ViewModel
import com.mekanly.data.DataItemBusinessProfile
import com.mekanly.data.dataModels.DataBusinessProfile
import com.mekanly.data.dataModels.DataBusinessProfileCategory
import com.mekanly.data.responseBody.ResponseBodyState
import com.mekanly.domain.useCase.UseCaseBusinessProfileCategories
import com.mekanly.domain.useCase.UseCaseBusinessProfiles
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
sealed class FragmentBusinessProfileState(){
    data class SuccessBusinessProfiles(val dataResponse:List<DataBusinessProfile>):FragmentBusinessProfileState()
    data class SuccessCategories(val dataResponse:List<DataBusinessProfileCategory>):FragmentBusinessProfileState()
    data object Loading:FragmentBusinessProfileState()
    data object Initial:FragmentBusinessProfileState()
    data class Error(val error:Any):FragmentBusinessProfileState()
}
class VMBusinessProfiles:ViewModel() {

    private val _fragmentState = MutableStateFlow<FragmentBusinessProfileState>(FragmentBusinessProfileState.Loading)
    val fragmentState: StateFlow<FragmentBusinessProfileState> = _fragmentState.asStateFlow()

    private val _isLoading = MutableStateFlow(true)

    private val _businessProfiles = MutableStateFlow<MutableList<DataBusinessProfile>>(mutableListOf())
    val businessProfiles: StateFlow<List<DataBusinessProfile>> = _businessProfiles.asStateFlow()

    private val useCase by lazy {
        UseCaseBusinessProfileCategories()
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
                  _fragmentState.value = FragmentBusinessProfileState.SuccessCategories(it.dataResponse as List<DataBusinessProfileCategory>)
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
                    val data = result.dataResponse as List<DataBusinessProfile>
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