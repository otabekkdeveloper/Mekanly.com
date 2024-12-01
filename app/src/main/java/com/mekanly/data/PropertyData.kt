package com.mekanly.data

data class PropertyData(
    val id: Int,
    val categoryId: Int,
    val categoryName: String,
    val title: String,
    val titleTwo: String,
    val description: String,
    val price: String,
    val imageResIds: List<String>
)

