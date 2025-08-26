package com.mekanly.data.models

data class ShopCategory(
    val id: Int,
    val title: String,
    val image: String?,
    val parent: String?,
    val description: String?,
    val subcategories: List<Subcategory>?
)