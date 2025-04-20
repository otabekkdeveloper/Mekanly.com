package com.mekanly.ui.bottomSheet.bottomSheetComments

import androidx.lifecycle.ViewModel
import com.mekanly.data.models.Banner
import com.mekanly.data.models.Comment
import com.mekanly.data.models.House
import com.mekanly.data.request.AddCommentBody
import com.mekanly.data.request.UpdateCommentBody
import com.mekanly.domain.model.ResponseBodyState
import com.mekanly.domain.useCase.AddCommentUseCase
import com.mekanly.domain.useCase.DeleteCommentUseCase
import com.mekanly.domain.useCase.GetCommentsUseCase
import com.mekanly.domain.useCase.UpdateCommentUseCase
import com.mekanly.ui.fragments.home.FragmentHomeState
import com.mekanly.utils.Constants.Companion.COMMENT_TYPE_HOUSE
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class VMComments : ViewModel() {

    private val getCommentsUseCase by lazy { GetCommentsUseCase() }
    private val updateCommentUseCase by lazy { UpdateCommentUseCase() }
    private val addCommentUseCase by lazy { AddCommentUseCase() }
    private val deleteCommentUseCase by lazy { DeleteCommentUseCase() }

    private val _commentsState = MutableStateFlow<ResponseBodyState>(ResponseBodyState.Loading)
    val commentsState: StateFlow<ResponseBodyState> = _commentsState.asStateFlow()

    private val _isLoading = MutableStateFlow(true)


    fun getComments(id: Long, type: String, start: Int = 0, limit: Int = 10) {
        getCommentsUseCase.execute(id, type, start, limit) {
            when (it) {
                is ResponseBodyState.Error -> {
                    _commentsState.value = ResponseBodyState.Error(4)
                }

                is ResponseBodyState.Loading -> {
                    _commentsState.value = ResponseBodyState.Loading
                }

                is ResponseBodyState.SuccessList -> {
                    if (it.dataResponse.isEmpty()) {
                        return@execute
                    } else {
                        _commentsState.value =
                            ResponseBodyState.SuccessList(it.dataResponse as MutableList<Comment>)
                    }
                }

                else -> {}
            }
        }

    }

    fun addComment(addCommentBody: AddCommentBody) {
        addCommentUseCase.execute(addCommentBody = addCommentBody) {
            when (it) {
                is ResponseBodyState.Error -> {
                    _isLoading.value = false
                }

                ResponseBodyState.Loading -> {
                    _isLoading.value = true
                }

                is ResponseBodyState.Success -> {
                    _isLoading.value = false
                }

                else -> {}
            }
        }
    }

    fun updateComment(id: Long, updateCommentBody: UpdateCommentBody) {
        updateCommentUseCase.execute(id, updateCommentBody = updateCommentBody) {
            when (it) {
                is ResponseBodyState.Error -> {
                    _isLoading.value = false
                }

                ResponseBodyState.Loading -> {
                    _isLoading.value = true
                }

                is ResponseBodyState.Success -> {
                    _isLoading.value = false
                }

                else -> {}
            }
        }
    }

    fun deleteComment(id: Long) {
        deleteCommentUseCase.execute(id) {
            when (it) {
                is ResponseBodyState.Error -> {
                    _isLoading.value = false
                }

                ResponseBodyState.Loading -> {
                    _isLoading.value = true
                }

                is ResponseBodyState.Success -> {
                    _isLoading.value = false
                }

                else -> {}
            }
        }
    }
}