package com.mekanly.domain.useCase

import com.mekanly.data.repository.RepositoryHouses
import com.mekanly.data.responseBody.ResponseBodyState

class GetHousesUseCase {
    private val rep by lazy {
        RepositoryHouses()
    }
     fun execute(callback: (ResponseBodyState) -> Unit){
         rep.getHouses(callback)
     }

    fun executeTopHouses(callback: (ResponseBodyState) -> Unit){
        rep.getTopHouses(callback)
    }
}