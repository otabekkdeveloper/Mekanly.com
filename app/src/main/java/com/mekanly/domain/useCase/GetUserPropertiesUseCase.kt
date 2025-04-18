package com.mekanly.domain.useCase

import com.mekanly.data.repository.HousesRepository
import com.mekanly.domain.model.ResponseBodyState


class GetUserPropertiesUseCase {

    private val rep by lazy { HousesRepository() }

    fun execute(callback: (ResponseBodyState) -> Unit){
        rep.getUserHouses(callback)
    }
}