package com.mekanly.presentation.ui.bottomSheet.bottomSheetComments

import androidx.lifecycle.ViewModel
import com.mekanly.domain.model.ResponseBodyState
import com.mekanly.domain.useCase.GetCommentsUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class VMComments : ViewModel() {

    private val useCase by lazy {
        GetCommentsUseCase()
    }

    private val _commentsState = MutableStateFlow<ResponseBodyState>(ResponseBodyState.Loading)
    val commentsState: StateFlow<ResponseBodyState> = _commentsState.asStateFlow()


    fun getHouseComments(houseId: Long) {
        useCase.execute(houseId) {
            _commentsState.value = it
        }
    }

    fun addComment(newCommentText: String) {

    }
}