package com.mekanly.domain.useCase

import com.mekanly.data.repository.HousesRepository
import com.mekanly.data.request.ReactionBody
import com.mekanly.domain.model.ResponseBodyState


class ToggleFavoritesUseCase {

    private val rep by lazy { HousesRepository() }

    fun execute(request: ReactionBody, callback: (ResponseBodyState) -> Unit){
        rep.toggleFavorite(request, callback)
    }
}