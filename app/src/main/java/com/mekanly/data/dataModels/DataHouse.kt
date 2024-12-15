package com.mekanly.data.dataModels

data class DataHouse(
    val id: Int,
    val category_id: Int,
    val category_name: String,
    val location: DataLocation,
    val name: String,
    val description: String,
    val price: String,
    val images: List<DataImage>,
    val possibilities: List<DataPossibility>
)