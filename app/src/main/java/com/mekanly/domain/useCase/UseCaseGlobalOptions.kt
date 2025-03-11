package com.mekanly.domain.useCase

import com.mekanly.data.repository.RepositoryGlobalOptions
import com.mekanly.data.responseBody.ResponseBodyState

class UseCaseGlobalOptions {
    private val rep by lazy {
        RepositoryGlobalOptions()
    }

    fun execute(callback: (ResponseBodyState) -> Unit){
        rep.getGlobalOptions(callback)
    }
}