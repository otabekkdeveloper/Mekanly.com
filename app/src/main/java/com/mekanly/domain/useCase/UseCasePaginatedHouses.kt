package com.mekanly.domain.useCase

import com.mekanly.data.dataModels.DataLocation
import com.mekanly.data.repository.RepositoryHouses
import com.mekanly.data.responseBody.DataHouseCategory
import com.mekanly.data.responseBody.ResponseBodyState

class UseCasePaginatedHouses {

    private val rep by lazy {
        RepositoryHouses()
    }
    fun execute(start:Long,callback: (ResponseBodyState) -> Unit){
        rep.getHousesPagination(start,callback =callback)
    }

    fun execute(start:Long, location: DataLocation?=null,category:DataHouseCategory?=null, callback: (ResponseBodyState) -> Unit){
        rep.getHousesPagination(start,location=location,category = category,callback = callback)
    }


    fun search(query:String,callback: (ResponseBodyState) -> Unit) {
        rep.searchHouses(query,callback)
    }
}