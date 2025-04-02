package com.mekanly.presentation.ui.fragments.register

import androidx.lifecycle.ViewModel
import com.mekanly.data.responseBody.ResponseBodyState
import com.mekanly.domain.useCase.UseCaseLogin
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class VMRegister : ViewModel() {


    private val _registerState = MutableStateFlow<ResponseBodyState>(ResponseBodyState.Initial)
    val registerState: StateFlow<ResponseBodyState> = _registerState.asStateFlow()

    private val useCase by lazy {
        UseCaseLogin()
    }

    fun login(phone: String) {
        useCase.execute(phone) {
            _registerState.value = it
        }
    }
}