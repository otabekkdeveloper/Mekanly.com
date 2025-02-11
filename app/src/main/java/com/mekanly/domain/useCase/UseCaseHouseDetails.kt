package com.mekanly.domain.useCase

import com.mekanly.data.repository.RepositoryHouses
import com.mekanly.data.responseBody.ResponseBodyState

class UseCaseHouseDetails {
    private val repository by lazy {
        RepositoryHouses()
    }


    fun execute(houseId: Long, callback: (ResponseBodyState) -> Unit) {
        repository.getHouseDetails(houseId, callback)
    }
}