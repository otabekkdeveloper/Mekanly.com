package com.mekanly.domain.useCase

import com.mekanly.data.repository.HousesRepository
import com.mekanly.data.request.AddHouseBody
import com.mekanly.domain.model.ResponseBodyState

class AddHouseUseCase {
    private val rep by lazy { HousesRepository() }

    fun addHouse(requestHouseBody:AddHouseBody, callback: (ResponseBodyState) -> Unit){
        rep.addHouse(requestHouseBody, emptyList(), callback)
    }
}