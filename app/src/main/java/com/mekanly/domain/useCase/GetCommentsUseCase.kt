package com.mekanly.domain.useCase

import com.mekanly.data.repository.CommentsRepository
import com.mekanly.domain.model.ResponseBodyState

class GetCommentsUseCase {
    private val rep by lazy { CommentsRepository() }

    fun execute(id:Long,callback: (ResponseBodyState) -> Unit){
        rep.getHouseComments(id,callback)
    }
}