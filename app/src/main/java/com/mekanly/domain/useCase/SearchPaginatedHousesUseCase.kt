package com.mekanly.domain.useCase

import com.mekanly.data.repository.HousesRepository
import com.mekanly.domain.model.ResponseBodyState

class SearchPaginatedHousesUseCase {

    private val rep by lazy { HousesRepository() }

    fun search(query:String,callback: (ResponseBodyState) -> Unit) {
        rep.searchHouses(query,callback)
    }
}