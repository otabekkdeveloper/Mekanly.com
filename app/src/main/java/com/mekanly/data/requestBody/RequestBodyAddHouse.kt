package com.mekanly.data.requestBody

import com.google.gson.annotations.SerializedName

data class RequestBodyAddHouse(
    val name:String,
    val description:String,
    @SerializedName("location_id")
    val locationId:Long,
    @SerializedName("category_id")
    val categoryId:Long,
    val images:List<Any>,
    val possibilities:List<Any>
)