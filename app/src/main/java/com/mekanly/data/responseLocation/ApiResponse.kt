package com.mekanly.data.responseLocation

data class ApiResponse(
    val data: List<House>
)

data class House(
    val id: Int,
    val category_id: Int,
    val category_name: String,
    val location: Location,
    val name: String,
    val description: String,
    val price: String,
    val images: List<Image>,
    val possibilities: List<Possibility>
)

data class Location(
    val id: Int,
    val parent_id: Int?,
    val name: String,
    val parent_name: String
)

data class Image(
    val id: Int,
    val url: String
)

data class Possibility(
    val id: Int,
    val name: String
)
