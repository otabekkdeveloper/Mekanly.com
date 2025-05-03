package com.mekanly.data.models

data class TopHouses(
    val data: List<TopHousesResponse>
)

data class TopHousesResponse(
    val item_id: Int,
    val type: String,
    val place: String?,    // null в JSON → String?
    val price: String,     // иногда String, иногда число → лучше String
    val name: String,
    val location: LocationTopHouses,
    val description: String,
    val phone: String,
    val image: String
)

data class LocationTopHouses(
    val id: Int,
    val name: String,
    val parent: String
)
