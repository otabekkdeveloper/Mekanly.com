package com.mekanly.data.models

import com.google.gson.annotations.SerializedName

data class Location(
    val id: Int,
    @SerializedName("parent_id") val parentId: Int?,
    @SerializedName("parent_name") val parentName: String?,
    val name: String,
    val children: List<Location>?
)