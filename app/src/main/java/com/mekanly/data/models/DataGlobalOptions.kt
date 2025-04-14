package com.mekanly.data.models

data class DataGlobalOptions(
    val locations: List<Location> = emptyList(),
    val houseCategories: List<HouseCategory> = emptyList(),
    val shopCategories: List<ShopCategory> = emptyList(),
    val possibility: List<Possibility> = emptyList(),
    val propertyType: List<PropertyType>  = emptyList(),
    val repairType: List<RepairType> = emptyList()
)