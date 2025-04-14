package com.mekanly.data.models

data class Subcategory(
    val id: Int,
    val title: String,
    val image: String?,
    val parent: String,
    val description: String?
)