package com.mekanly.domain.useCase

import com.mekanly.data.repository.RepositoryBanner
import com.mekanly.data.responseBody.ResponseBodyState

class GetBannersUseCase {
    private val rep by lazy {
        RepositoryBanner()
    }

    fun execute(callback: (ResponseBodyState) -> Unit) {
        rep.getBanners(callback)
    }
}