package com.mekanly.domain.useCase

import com.mekanly.data.repository.BusinessProfilesRepository
import com.mekanly.domain.model.ResponseBodyState

class GetBusinessCategoriesUseCase {
    private val rep by lazy { BusinessProfilesRepository() }

    fun execute(callback:(ResponseBodyState)->Unit){
        rep.getBusinessProfileCategories(callback)
    }

}

