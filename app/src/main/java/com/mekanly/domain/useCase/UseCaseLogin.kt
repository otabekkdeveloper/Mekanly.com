package com.mekanly.domain.useCase

import com.mekanly.data.repository.RepositoryRegister
import com.mekanly.data.requestBody.RequestBodyConfirmation
import com.mekanly.data.requestBody.RequestBodyLogin
import com.mekanly.data.responseBody.ResponseBodyState

class UseCaseLogin {

    private val rep by lazy {
        RepositoryRegister()
    }

    fun execute(phone: String, callback: (ResponseBodyState) -> Unit) {
        val loginRequestBody = RequestBodyLogin(phone = phone)
        rep.login(loginRequestBody, callback)
    }

    fun executeConfirmation(
        phone: String, tokenOnWaitlist: String, code: String, callback: (ResponseBodyState) -> Unit
    ) {
        val confirmBody =
            RequestBodyConfirmation(phone = phone, token = tokenOnWaitlist, code = code)

        rep.confirmLogin(confirmBody, callback)
    }
}