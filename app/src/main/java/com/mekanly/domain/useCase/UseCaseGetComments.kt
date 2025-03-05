package com.mekanly.domain.useCase

import com.mekanly.data.repository.RepositoryComments
import com.mekanly.data.responseBody.ResponseBodyState

class UseCaseGetComments {
    private val rep by lazy {
        RepositoryComments()
    }
    fun execute(id:Long,callback: (ResponseBodyState) -> Unit){
        rep.getHouseComments(id,callback)
    }
}