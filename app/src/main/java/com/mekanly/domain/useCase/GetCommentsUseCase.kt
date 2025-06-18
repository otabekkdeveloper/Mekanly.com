package com.mekanly.domain.useCase

import com.mekanly.data.repository.CommentsRepository
import com.mekanly.domain.model.ResponseBodyState

class GetCommentsUseCase {
    private val rep by lazy { CommentsRepository() }

    fun execute(id:Long, type:String, start:Int, limit:Int = 10, callback: (ResponseBodyState) -> Unit){
        rep.getComments(id, type, start, limit, callback)
    }
}