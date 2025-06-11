package com.mekanly.domain.useCase

import com.mekanly.data.models.FavoritesRequest
import com.mekanly.data.repository.HousesRepository
import com.mekanly.domain.model.ResponseBodyState


class GetFavoriteHouseUseCase {

    private val rep by lazy { HousesRepository() }

    fun execute(request: FavoritesRequest, callback: (ResponseBodyState) -> Unit){
        rep.getFavoriteHouses(request, callback)
    }

    fun invoke(request: FavoritesRequest, callback: (ResponseBodyState) -> Unit){
        rep.getFavoriteProducts(request, callback)
    }
}