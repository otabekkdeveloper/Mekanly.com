package com.mekanly.domain.useCase

import com.mekanly.data.repository.BusinessProfilesRepository
import com.mekanly.domain.model.ResponseBodyState

class UseCaseGetCategoryProducts {

    private val rep by lazy {
        BusinessProfilesRepository()
    }

    fun execute(
        categoryId: Int,
        offset: Int? = 0,
        limit: Int? = 10,
        callback: (ResponseBodyState) -> Unit
    ) {
        rep.getCategoryProducts(categoryId, offset, limit, callback)

    }
}