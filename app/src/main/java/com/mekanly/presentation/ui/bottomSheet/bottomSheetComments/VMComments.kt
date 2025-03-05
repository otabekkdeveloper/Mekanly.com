package com.mekanly.presentation.ui.bottomSheet.bottomSheetComments

import androidx.lifecycle.ViewModel
import com.mekanly.data.responseBody.ResponseBodyState
import com.mekanly.domain.useCase.UseCaseGetComments
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class VMComments : ViewModel() {

    private val useCase by lazy {
        UseCaseGetComments()
    }

    private val _commentsState = MutableStateFlow<ResponseBodyState>(ResponseBodyState.Loading)
    val commentsState: StateFlow<ResponseBodyState> = _commentsState.asStateFlow()


    fun getHouseComments(houseId: Long) {
        useCase.execute(houseId) {
            _commentsState.value = it
        }
    }
}