package com.mekanly.domain.useCase

import com.mekanly.data.repository.CommentsRepository
import com.mekanly.data.request.AddCommentBody
import com.mekanly.domain.model.ResponseBodyState

class DeleteCommentUseCase {
    private val rep by lazy { CommentsRepository() }

    fun execute(id: Long, callback: (ResponseBodyState) -> Unit){
        rep.deleteComment(id, callback)
    }
}