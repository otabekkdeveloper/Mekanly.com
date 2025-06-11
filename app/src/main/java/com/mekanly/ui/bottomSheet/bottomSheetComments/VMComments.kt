package com.mekanly.ui.bottomSheet.bottomSheetComments

import androidx.lifecycle.ViewModel
import com.mekanly.data.local.preferences.AppPreferences
import com.mekanly.data.models.Comment
import com.mekanly.data.request.AddCommentBody
import com.mekanly.data.request.UpdateCommentBody
import com.mekanly.domain.model.ResponseBodyState
import com.mekanly.domain.useCase.AddCommentUseCase
import com.mekanly.domain.useCase.DeleteCommentUseCase
import com.mekanly.domain.useCase.GetCommentsUseCase
import com.mekanly.domain.useCase.UpdateCommentUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

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
                        _commentsState.value = ResponseBodyState.SuccessList(it.dataResponse.toMutableList())
                    }
                }

                else -> {}
            }
        }
    }

    fun addComment(addCommentBody: AddCommentBody, onSuccess: () -> Unit) {
        addCommentUseCase.execute(addCommentBody = addCommentBody) {
            when (it) {
                is ResponseBodyState.Error -> {
                    _isLoading.value = false
                    // Здесь можно добавить обработку ошибки - удалить комментарий из UI если он не отправился
                }

                ResponseBodyState.Loading -> {
                    _isLoading.value = true
                }

                is ResponseBodyState.Success -> {
                    _isLoading.value = false
                    // Комментарий успешно отправлен на сервер
                    // UI уже обновился в BottomSheetComments, здесь просто вызываем callback
                    onSuccess()
                }

                else -> {}
            }
        }
    }

    fun appendCommentToList(newComment: Comment) {
        val currentState = _commentsState.value
        if (currentState is ResponseBodyState.SuccessList) {
            val currentList = (currentState.dataResponse as? MutableList<Comment>)?.toMutableList() ?: mutableListOf()
            currentList.add(0, newComment)
            _commentsState.value = ResponseBodyState.SuccessList(currentList)
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