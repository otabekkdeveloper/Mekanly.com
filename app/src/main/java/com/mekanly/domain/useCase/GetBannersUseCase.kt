package com.mekanly.domain.useCase

import com.mekanly.data.repository.BannerRepository
import com.mekanly.domain.model.ResponseBodyState

class GetBannersUseCase {
    private val rep by lazy { BannerRepository() }

    fun execute(callback: (ResponseBodyState) -> Unit) {
        rep.getBanners(callback)
    }
}