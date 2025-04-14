package com.mekanly.domain.useCase

import com.mekanly.data.repository.HousesRepository
import com.mekanly.domain.model.ResponseBodyState

class GetHouseDetailsUseCase {
    private val repository by lazy { HousesRepository() }

    fun execute(houseId: Long, callback: (ResponseBodyState) -> Unit) {
        repository.getHouseDetails(houseId, callback)
    }
}