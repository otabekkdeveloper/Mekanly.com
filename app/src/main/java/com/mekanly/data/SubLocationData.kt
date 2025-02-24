package com.mekanly.data

data class SubLocationData(
    val id: Int,
    val parent_id: Int?,
    val name: String,
    val created_at: String,
    val updated_at: String,
    val parent_name: String
)