package com.mekanly.presentation.ui.fragments.home

import android.util.Log
import androidx.lifecycle.ViewModel
import com.mekanly.data.models.Banner
import com.mekanly.data.models.House
import com.mekanly.domain.model.ResponseBodyState
import com.mekanly.domain.useCase.GetBannersUseCase
import com.mekanly.domain.useCase.GetHousesUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

sealed class FragmentHomeState(){
    data class SuccessBanners(val dataResponse:List<Banner>):FragmentHomeState()
    data class SuccessTopHouses(val dataResponse:List<House>):FragmentHomeState()
    data object Loading:FragmentHomeState()
    data object Initial:FragmentHomeState()
    data class Error(val error:Any):FragmentHomeState()
}
class VMHome:ViewModel() {

    private val _homeState = MutableStateFlow<FragmentHomeState>(FragmentHomeState.Initial)
    val homeState: StateFlow<FragmentHomeState> = _homeState.asStateFlow()

    private val _banners= MutableStateFlow(emptyList<Banner>().toMutableList())
    val banners: StateFlow<MutableList<Banner>> = _banners.asStateFlow()

    private val _houses= MutableStateFlow(emptyList<House>().toMutableList())
    val houses: StateFlow<MutableList<House>> = _houses.asStateFlow()

    private val useCase by lazy {
        GetHousesUseCase()
    }

    private val useCaseBanners by lazy {
        GetBannersUseCase()
    }

    init {
        getBanners()
//        getTopHouses()
    }

    private fun getBanners(){
        Log.e("BANNERS", "getBanners: now getting banners" )
        useCaseBanners.execute {
           when(it){
               is ResponseBodyState.Error -> {
                   _homeState.value = FragmentHomeState.Error(4)
               }
               ResponseBodyState.Loading -> {
                   _homeState.value = FragmentHomeState.Loading
               }
               is ResponseBodyState.SuccessList -> {
                   if (it.dataResponse.isEmpty()){
                       _homeState.value = FragmentHomeState.Error(4)
                       return@execute
                   }else{
                       _homeState.value = FragmentHomeState.SuccessBanners(it.dataResponse as MutableList<Banner> )
                       _banners.value = it.dataResponse
                   }
               }

               else -> {}
           }
        }
    }

    private fun getTopHouses(){

        useCase.execute {
            when(it){
                is ResponseBodyState.Error -> {
                    _homeState.value = FragmentHomeState.Error(4)
                }
                ResponseBodyState.Loading -> {
                    _homeState.value = FragmentHomeState.Loading
                }
                is ResponseBodyState.SuccessList -> {
                    if (it.dataResponse.isEmpty()){
                        return@execute
                    }else{
                        val list = (it.dataResponse as MutableList<House>).take(50).toMutableList()
                        _houses.value = list
                        _homeState.value = FragmentHomeState.SuccessTopHouses(list)
                    }
                }
                else -> {}
            }
        }
    }
}