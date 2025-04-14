package com.mekanly.domain.useCase

import com.mekanly.data.repository.RegisterRepository
import com.mekanly.data.request.AuthBody
import com.mekanly.data.request.ConfirmationBody
import com.mekanly.domain.model.ResponseBodyState

class LoginUseCase {

    private val rep by lazy {
        RegisterRepository()
    }

    fun execute(phone: String, callback: (ResponseBodyState) -> Unit) {
        val loginRequestBody = AuthBody(phone = phone)
        rep.login(loginRequestBody, callback)
    }

    fun executeConfirmation(
        phone: String, tokenOnWaitlist: String, code: String, callback: (ResponseBodyState) -> Unit
    ) {
        val confirmBody =
            ConfirmationBody(phone = phone, token = tokenOnWaitlist, code = code)

        rep.confirmLogin(confirmBody, callback)
    }
}

