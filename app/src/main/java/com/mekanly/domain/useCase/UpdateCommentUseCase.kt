package com.mekanly.domain.useCase

import com.mekanly.data.repository.CommentsRepository
import com.mekanly.data.request.UpdateCommentBody
import com.mekanly.domain.model.ResponseBodyState

class UpdateCommentUseCase {
    private val rep by lazy { CommentsRepository() }

    fun execute(id:Long, updateCommentBody: UpdateCommentBody, callback: (ResponseBodyState) -> Unit){
        rep.updateComment(id, updateCommentBody, callback)
    }
}