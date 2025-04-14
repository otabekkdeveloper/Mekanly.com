package com.mekanly.domain.useCase

import com.mekanly.data.repository.GlobalOptionsRepository
import com.mekanly.data.responseBody.ResponseBodyState

class UseCaseGlobalOptions {
    private val rep by lazy {
        GlobalOptionsRepository()
    }

    fun execute(callback: (ResponseBodyState) -> Unit){
        rep.getGlobalOptions(callback)
    }
}