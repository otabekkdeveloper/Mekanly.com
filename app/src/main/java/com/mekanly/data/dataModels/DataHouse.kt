package com.mekanly.data.dataModels
import com.google.gson.annotations.SerializedName

data class DataHouse(
    @SerializedName("id")
    val id: Int,
    @SerializedName("category_id")
    val categoryId: Int,
    @SerializedName("category_name")
    val categoryName: String,
    @SerializedName("location")
    val location: DataLocation,
    @SerializedName("enter_time")
    val enterTime: String,
    @SerializedName("leave_time")
    val leaveTime: String,
    @SerializedName("day_enter_time")
    val dayEnterTime: String,
    @SerializedName("day_leave_time")
    val dayLeaveTime: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("description")
    val description: String,
    @SerializedName("reason")
    val reason: String?,
    @SerializedName("viewed")
    val viewed: Int,
    @SerializedName("room_number")
    val roomNumber: Int,
    @SerializedName("floor_number")
    val floorNumber: Int,
    @SerializedName("guest_number")
    val guestNumber: Int,
    @SerializedName("order")
    val order: String?,
    @SerializedName("price")
    val price: String,
    @SerializedName("status")
    val status: String,
    @SerializedName("luxe")
    val luxe: Int,
    @SerializedName("bron_number")
    val bronNumber: String,
    @SerializedName("bron_status")
    val bronStatus: Int,
    @SerializedName("user")
    val user: DataUser,
    val images:List<DataImage>,
    val possibilities:List<DataPossibility>
)
