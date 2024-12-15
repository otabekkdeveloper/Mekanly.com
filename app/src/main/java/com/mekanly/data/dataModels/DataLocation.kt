package com.mekanly.data.dataModels

data class DataLocation(
    val id: Int,
    val parent_id: Int?,
    val name: String,
    val parent_name: String
)