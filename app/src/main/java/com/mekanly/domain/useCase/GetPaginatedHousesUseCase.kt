package com.mekanly.domain.useCase

import com.mekanly.data.repository.HousesRepository
import com.mekanly.data.request.FilterBody
import com.mekanly.domain.model.ResponseBodyState

class GetPaginatedHousesUseCase {

    private val rep by lazy { HousesRepository() }

    fun execute(filterBody: FilterBody, callback: (ResponseBodyState) -> Unit){
        rep.getHousesPagination(filterBody = filterBody,callback = callback)
    }
}

