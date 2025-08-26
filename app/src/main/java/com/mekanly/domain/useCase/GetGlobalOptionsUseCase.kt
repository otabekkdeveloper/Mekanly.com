package com.mekanly.domain.useCase

import com.mekanly.data.repository.GlobalOptionsRepository
import com.mekanly.domain.model.ResponseBodyState

class GetGlobalOptionsUseCase {
    private val rep by lazy { GlobalOptionsRepository() }

    fun execute(callback: (ResponseBodyState) -> Unit){
        rep.getGlobalOptions(callback)
    }
}