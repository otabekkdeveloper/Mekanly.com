package com.mekanly.domain.useCase

import androidx.loader.app.LoaderManager.LoaderCallbacks
import com.mekanly.data.repository.RepositoryRegister
import com.mekanly.data.requestBody.RequestBodyRegister
import com.mekanly.data.responseBody.ResponseBodyState

class RegisterUseCase {
    private val rep by lazy {
        RepositoryRegister()
    }

    fun execute(phone:String, callback:(ResponseBodyState)->Unit){
        val registerBody =RequestBodyRegister(phone)
        rep.register(registerBody,callback)
    }
}