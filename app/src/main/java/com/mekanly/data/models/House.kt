package com.mekanly.data.models
import com.google.gson.annotations.SerializedName

data class House(
    @SerializedName("id")
    val id: Int,
    @SerializedName("category_name")
    val categoryName: String,
    @SerializedName("location")
    val location: Location,
    @SerializedName("user_id")
    val userId: Int,
    @SerializedName("username")
    val username: String,
    @SerializedName("user_phone")
    val userPhone: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("description")
    val description: String,
    @SerializedName("price")
    val price: String,
    @SerializedName("viewed")
    val viewed: Int,
    @SerializedName("star")
    val star: Boolean?,
    @SerializedName("room_number")
    val roomNumber: Int?,
    @SerializedName("floor_number")
    val floorNumber: Int?,
    @SerializedName("property_type")
    val propertyType: Option?,
    @SerializedName("repair_type")
    val repairType: Option?,
    @SerializedName("status")
    val status: String,
    @SerializedName("luxe")
    val luxe: Boolean,
    @SerializedName("luxe_status")
    val luxeStatus: Boolean,
    @SerializedName("luxe_expire")
    val luxeExpire: String?,
    @SerializedName("vip_status")
    val vipStatus: Boolean,
    @SerializedName("vip_expire")
    val vipExpire: String?,
    @SerializedName("bron_number")
    val bronNumber: String,
    @SerializedName("images")
    val images: List<Image>,
    @SerializedName("possibilities")
    val possibilities: List<Option>,
    val commentCount: Long,
    @SerializedName("is_comment")
    val isComment: Boolean?,
    @SerializedName("who")
    val who: String?,
    @SerializedName("area")
    val area: String?,
    @SerializedName("exclusisive")
    val exclusive: Int,
    @SerializedName("favorited")
    var favorite: Boolean,
    @SerializedName("hashtag")
    val hashtag: String?,
    @SerializedName("level_number")
    val levelNumber: Int?,
    @SerializedName("liked")
    val liked: Boolean,
    @SerializedName("shop")
    val shop: BusinessProfile?,
    @SerializedName("type")
    val type: String,
    @SerializedName("date")
    val date: String,
    @SerializedName("updated_at")
    val updatedAt: String,
    @SerializedName("created_at")
    val createdAt: String
)


