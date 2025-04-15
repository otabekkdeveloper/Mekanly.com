package com.mekanly.data.request

import com.google.gson.annotations.SerializedName


data class FilterRequest(
    @SerializedName("category_id")
    val categoryId: Int? = null,
    @SerializedName("location_id")
    val locationId: Int? = null,
    @SerializedName("start_date")
    val startDate: String? = null,
    @SerializedName("end_date")
    val endDate: String? = null,
    val possibilities: List<Int>? = null,
    val image: Boolean? = null,
    val who: String? = null,
    @SerializedName("cheap_price")
    val cheapPrice: Int? = null,
    @SerializedName("expensive_price")
    val expensivePrice: Int? = null,
    @SerializedName("small_area")
    val smallArea: Int? = null,
    @SerializedName("big_area")
    val bigArea: Int? = null,
    val location: List<Int>? = null,
    val categories: List<Int>? = null,
    @SerializedName("property_type")
    val propertyType: List<Int>? = null,
    @SerializedName("repair_type")
    val repairType: List<Int>? = null,
    val status: String? = null, // e.g. "VIP" or "Luxe"
    @SerializedName("room_number")
    val roomNumber: List<Int>? = null,
    @SerializedName("floor_number")
    val floorNumber: List<Int>? = null,
    @SerializedName("sort_by")
    val sortBy: String? = null, // "price", "created_at"
    @SerializedName("sort_order")
    val sortOrder: String? = null, // "asc", "desc"
    val limit: Int? = 15,
    val offset: Int? = 0
)