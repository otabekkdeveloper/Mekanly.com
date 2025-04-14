package com.mekanly.domain.useCase

import com.mekanly.data.repository.CommentsRepository
import com.mekanly.data.responseBody.ResponseBodyState

class UseCaseGetComments {
    private val rep by lazy {
        CommentsRepository()
    }
    fun execute(id:Long,callback: (ResponseBodyState) -> Unit){
        rep.getHouseComments(id,callback)
    }
}