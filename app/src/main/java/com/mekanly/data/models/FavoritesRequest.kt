package com.mekanly.data.models

data class FavoritesRequest(
    val type: String,
    val category_id: Int?,
    val limit: Int,
    val offset: Int
)
