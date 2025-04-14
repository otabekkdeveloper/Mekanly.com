package com.mekanly.domain.useCase

import com.mekanly.data.repository.HousesRepository
import com.mekanly.domain.model.ResponseBodyState

class GetHousesUseCase {
    private val rep by lazy {
        HousesRepository()
    }
     fun execute(callback: (ResponseBodyState) -> Unit){
         rep.getHouses(callback)
     }

    fun executeTopHouses(callback: (ResponseBodyState) -> Unit){
        rep.getTopHouses(callback)
    }
}