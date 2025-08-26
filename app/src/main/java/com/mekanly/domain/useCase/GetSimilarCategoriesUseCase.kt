package com.mekanly.domain.useCase

import com.mekanly.data.repository.BusinessProfilesRepository
import com.mekanly.domain.model.ResponseBodyState

class GetSimilarCategoriesUseCase {
    private val rep by lazy {
        BusinessProfilesRepository()
    }

    fun invoke(id:Int,callback:(ResponseBodyState)->Unit ){
        rep.getBusinessProfileCategories(callback)
    }
}