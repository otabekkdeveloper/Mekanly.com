package com.mekanly.domain.useCase

import com.mekanly.data.repository.RepositoryBusinessProfiles
import com.mekanly.data.responseBody.ResponseBodyState

class UseCaseBusinessProfileCategories {
    private val rep by lazy {
        RepositoryBusinessProfiles()
    }

    fun execute(callback:(ResponseBodyState)->Unit){
        rep.getBusinessProfileCategories(callback)
    }

    fun executeSimilarCategories(id:Int,callback:(ResponseBodyState)->Unit ){
        rep.getBusinessProfileCategories(callback)
    }
}