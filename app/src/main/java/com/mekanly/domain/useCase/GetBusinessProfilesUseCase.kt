package com.mekanly.domain.useCase

import com.mekanly.data.repository.BusinessProfilesRepository
import com.mekanly.domain.model.ResponseBodyState

class GetBusinessProfilesUseCase {
    private val rep by lazy { BusinessProfilesRepository() }

    fun invoke(start: Long, callback: (ResponseBodyState) -> Unit) {
        rep.getBusinessProfilesWithPagination(start, callback)
    }
}

