package com.mekanly.domain.useCase

import com.mekanly.data.models.Location
import com.mekanly.data.models.PriceRange
import com.mekanly.data.repository.HousesRepository
import com.mekanly.data.models.HouseCategory
import com.mekanly.data.responseBody.ResponseBodyState

class UseCasePaginatedHouses {

    private val rep by lazy {
        HousesRepository()
    }

    fun execute(start:Long, location: Location?=null, category: HouseCategory?=null, priceRange: PriceRange?=null, callback: (ResponseBodyState) -> Unit){
        rep.getHousesPagination(start,location=location,category = category,priceRange=priceRange,callback = callback)
    }


    fun search(query:String,callback: (ResponseBodyState) -> Unit) {
        rep.searchHouses(query,callback)
    }
}