package com.mekanly.domain.useCase

import com.mekanly.data.dataModels.DataLocation
import com.mekanly.data.repository.RepositoryHouses
import com.mekanly.data.responseBody.ResponseBodyState

class UseCasePaginatedHouses {

    private val rep by lazy {
        RepositoryHouses()
    }
    fun execute(start:Long,callback: (ResponseBodyState) -> Unit){
        rep.getHousesPagination(start,callback =callback)
    }

    fun execute(start:Long, location: DataLocation?=null, callback: (ResponseBodyState) -> Unit){
        rep.getHousesPagination(start,location=location,callback = callback)
    }


    fun search(query:String,callback: (ResponseBodyState) -> Unit) {
        rep.searchHouses(query,callback)
    }
}