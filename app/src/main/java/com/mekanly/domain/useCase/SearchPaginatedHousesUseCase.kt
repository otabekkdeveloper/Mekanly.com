package com.mekanly.domain.useCase

import com.mekanly.data.repository.HousesRepository
import com.mekanly.domain.model.ResponseBodyState

class SearchPaginatedHousesUseCase {

    private val rep by lazy { HousesRepository() }

    fun search(query: String, start: Int, limit: Int, callback: (ResponseBodyState) -> Unit) {
        rep.searchHouses(query, start, limit, callback)
    }
}