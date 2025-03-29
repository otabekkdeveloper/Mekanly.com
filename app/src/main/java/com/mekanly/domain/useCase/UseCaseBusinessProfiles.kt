package com.mekanly.domain.useCase

import com.mekanly.data.repository.RepositoryBusinessProfiles
import com.mekanly.data.responseBody.ResponseBodyState

class UseCaseBusinessProfiles {
    private val rep by lazy {
        RepositoryBusinessProfiles()
    }
    fun execute(start:Long,callback: (ResponseBodyState) -> Unit){
        rep.getBusinessProfilesWithPagination(start,callback)
    }
}