package com.mekanly.data.responseBody

import com.mekanly.data.dataModels.DataLocation
import com.mekanly.data.dataModels.DataPossibility

data class ResponseGlobalOptions(
    val data: DataGlobalOptions
)

data class DataGlobalOptions(
    val locations: List<DataLocation> = emptyList(),
    val categoryHouses: List<DataHouseCategory> = emptyList(),
    val categoryShops: List<CategoryShop> = emptyList(),
    val possibility: List<DataPossibility> = emptyList(),
    val propertyType: List<PropertyType>  = emptyList(),
    val repairType: List<RepairType> = emptyList()
)

data class DataHouseCategory(
    val id: Int,
    val parent_id: Int?,
    val name: String,
    val created_at: String,
    val updated_at: String
)

data class CategoryShop(
    val id: Int,
    val title: String,
    val image: String?,
    val parent: String?,
    val description: String?,
    val created_at: String,
    val updated_at: String,
    val subcategories: List<Subcategory>?
)

data class Subcategory(
    val id: Int,
    val title: String,
    val image: String?,
    val parent: String,
    val description: String?,
    val created_at: String,
    val updated_at: String
)


data class PropertyType(
    val id: Int,
    val name: String,
    val icon: String?
)

data class RepairType(
    val id: Int,
    val name: String,
    val icon: String?
)