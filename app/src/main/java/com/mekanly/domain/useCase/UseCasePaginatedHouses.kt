package com.mekanly.domain.useCase

import com.mekanly.data.repository.RepositoryHouses
import com.mekanly.data.responseBody.ResponseBodyState

class UseCasePaginatedHouses {

    private val rep by lazy {
        RepositoryHouses()
    }
    fun execute(start:Long,callback: (ResponseBodyState) -> Unit){
        rep.getHousesPagination(start,callback)
    }

    fun search(query:String,callback: (ResponseBodyState) -> Unit) {
        rep.searchHouses(query,callback)
    }
}