package com.mekanly.ui.fragments.register

import androidx.lifecycle.ViewModel
import com.mekanly.domain.model.ResponseBodyState
import com.mekanly.domain.useCase.LoginUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class VMRegister : ViewModel() {


    private val _registerState = MutableStateFlow<ResponseBodyState>(ResponseBodyState.Initial)
    val registerState get() = _registerState.asStateFlow()

    private val useCase by lazy {
        LoginUseCase()
    }

    fun login(phone: String) {
        useCase.execute(phone) {
            _registerState.value = it

        }
    }
}