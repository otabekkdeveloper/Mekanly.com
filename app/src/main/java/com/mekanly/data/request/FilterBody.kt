package com.mekanly.data.request

import com.google.gson.annotations.SerializedName
import com.mekanly.data.repository.HousesRepository.Companion.LIMIT_REGULAR


data class FilterBody(
    @SerializedName("search")
    val search: String = "",
    @SerializedName("category_id")
    val categoryId: Int? = null,
    @SerializedName("location_id")
    val locationId: Int? = null,
    @SerializedName("start_date")
    val startDate: String? = null,
    @SerializedName("end_date")
    val endDate: String? = null,
    val possibilities: String? = null,
    val image: Boolean? = null,
    val who: String? = null,
    @SerializedName("cheap_price")
    val minPrice: Int? = null,
    @SerializedName("expensive_price")
    val maxPrice: Int? = null,
    @SerializedName("small_area")
    val minArea: Int? = null,
    @SerializedName("big_area")
    val maxArea: Int? = null,
    val location: String? = null,
    val categories: String? = null,
    @SerializedName("property_type")
    val propertyType: String? = null,
    @SerializedName("repair_type")
    val repairType: String? = null,
    val status: String? = null, // e.g. "VIP" or "Luxe"
    @SerializedName("room_number")
    val roomNumber: String? = null,
    @SerializedName("floor_number")
    val floorNumber: String? = null,
    @SerializedName("sort_by")
    val sortBy: String? = "created_at", // "price", "created_at"
    @SerializedName("sort_order")
    val sortOrder: String? = "desc", // "asc", "desc"
    var limit: Int? = LIMIT_REGULAR.toInt(),
    var offset: Int? = 0
)