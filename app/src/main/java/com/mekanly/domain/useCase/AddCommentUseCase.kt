package com.mekanly.domain.useCase

import com.mekanly.data.repository.CommentsRepository
import com.mekanly.data.request.AddCommentBody
import com.mekanly.domain.model.ResponseBodyState

class AddCommentUseCase {
    private val rep by lazy { CommentsRepository() }

    fun execute(addCommentBody: AddCommentBody, callback: (ResponseBodyState) -> Unit){
        rep.addComment(addCommentBody, callback)
    }
}