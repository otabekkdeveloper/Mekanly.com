package com.mekanly.presentation.ui.fragments.home

import androidx.lifecycle.ViewModel
import com.mekanly.data.dataModels.DataBanner
import com.mekanly.data.responseBody.ResponseBodyState
import com.mekanly.domain.useCase.GetBannersUseCase
import com.mekanly.domain.useCase.GetHousesUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class VMHome:ViewModel() {

    private val _homeState = MutableStateFlow<ResponseBodyState>(ResponseBodyState.Loading)
    val homeState: StateFlow<ResponseBodyState> = _homeState.asStateFlow()

    private val _banners= MutableStateFlow(emptyList<DataBanner>().toMutableList())
    val banners: StateFlow<MutableList<DataBanner>> = _banners.asStateFlow()

    private val useCase by lazy {
        GetHousesUseCase()
    }

    private val useCaseBanners by lazy {
        GetBannersUseCase()
    }

    init {
//        getHouses()
        getBanners()
    }

    private fun getHouses() {
        useCase.execute {
            _homeState.value = it
        }
    }

    private fun getBanners(){
        useCaseBanners.execute {
            _homeState.value = it
           when(it){
               is ResponseBodyState.Error -> {
                   _homeState.value = ResponseBodyState.Error(4)
               }
               ResponseBodyState.Loading -> {
                   _homeState.value = ResponseBodyState.Loading
               }
               is ResponseBodyState.SuccessList -> {
                   if (it.dataResponse.isEmpty()){
                       return@execute
                   }else{
                       _banners.value = it.dataResponse as MutableList<DataBanner>
                   }
               }

               else -> {}
           }
        }
    }
}