package com.mekanly.domain.useCase

import com.mekanly.data.repository.BusinessProfilesRepository
import com.mekanly.domain.model.ResponseBodyState

class GetSimilarBusinessCategoriesUseCase {
    private val rep by lazy { BusinessProfilesRepository() }

    fun invoke(id: Long, callback: (ResponseBodyState) -> Unit) {
        rep.getSimilarBusinessProfileCategories(id, callback)
    }
}
