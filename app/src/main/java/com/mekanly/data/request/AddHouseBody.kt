package com.mekanly.data.request

import com.google.gson.annotations.SerializedName
import com.mekanly.utils.Constants.Companion.OWNER

data class AddHouseBody(
    var name: String? = null,
    var description: String? = null,
    var price: Int? = null,
    @SerializedName("location_id")
    var locationId: Int? = null,
    @SerializedName("category_id")
    var categoryId: Int? = null,
    @SerializedName("property_type_id")
    var propertyTypeId: Int? = null,
    @SerializedName("repair_type_id")
    var repairTypeId: Int? = null,
    var possibilities: List<Int>? = null,
    var who: String = OWNER,
    var area: Int? = null,
    @SerializedName("write_comment")
    var writeComment: Int? = null,
    @SerializedName("floor_number")
    var floorNumber: Int? = null,
    @SerializedName("room_number")
    var roomNumber: Int? = null,
    @SerializedName("level_number")
    var levelNumber: Int? = null,
    var exclusive: Int? = null,
    var hashtag: String? = null,
)
