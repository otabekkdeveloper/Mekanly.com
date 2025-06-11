package com.mekanly.data.models

import com.google.gson.annotations.SerializedName

data class DataGlobalOptions(
    val locations: List<Location> = emptyList(),
    val priceRange: List<PriceRange> = emptyList(),
    @SerializedName("categoryHouses")
    val houseCategories: List<HouseCategory> = emptyList(),
    @SerializedName("categoryShops")
    val shopCategories: List<ShopCategory> = emptyList(),
    val possibility: List<Option> = emptyList(),
    val propertyType: List<Option>  = emptyList(),
    val repairType: List<Option> = emptyList(),
    val report: List<Report> = emptyList()
)