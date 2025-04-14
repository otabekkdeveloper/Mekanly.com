package com.mekanly.domain.useCase

import com.mekanly.data.repository.BusinessProfilesRepository
import com.mekanly.data.responseBody.ResponseBodyState

class GetBusinessCategoriesUseCase {
    private val rep by lazy {
        BusinessProfilesRepository()
    }

    fun execute(callback:(ResponseBodyState)->Unit){
        rep.getBusinessProfileCategories(callback)
    }

    fun executeSimilarCategories(id:Int,callback:(ResponseBodyState)->Unit ){
        rep.getBusinessProfileCategories(callback)
    }
}