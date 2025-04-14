package com.mekanly.domain.useCase

import com.mekanly.data.repository.RegisterRepository
import com.mekanly.data.request.AuthBody
import com.mekanly.data.responseBody.ResponseBodyState

class RegisterUseCase {
    private val rep by lazy {
        RegisterRepository()
    }

    fun execute(phone:String, callback:(ResponseBodyState)->Unit){
        val registerBody = AuthBody(phone)
        rep.register(registerBody,callback)
    }
}