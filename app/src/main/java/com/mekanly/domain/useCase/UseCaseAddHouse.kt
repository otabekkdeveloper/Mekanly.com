package com.mekanly.domain.useCase

import com.mekanly.data.repository.RepositoryHouses
import com.mekanly.data.requestBody.RequestBodyAddHouse
import com.mekanly.data.responseBody.ResponseBodyState

class UseCaseAddHouse {
    private val rep by lazy {
        RepositoryHouses()
    }

    fun addHouse(requestHouseBody:RequestBodyAddHouse,  callback: (ResponseBodyState) -> Unit){
        rep.addHouse(requestHouseBody,callback)
    }
}