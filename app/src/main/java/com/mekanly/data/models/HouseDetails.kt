package com.mekanly.data.models

import com.google.gson.annotations.SerializedName


data class HouseDetails(
    @SerializedName("id")
    val id: Int,
    @SerializedName("category_id")
    val categoryId: Int,
    @SerializedName("category_name")
    val categoryName: String,
    @SerializedName("location")
    val location: Location,
    @SerializedName("username")
    val username: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("description")
    val description: String,
    @SerializedName("reason")
    val reason: String?,
    @SerializedName("price")
    val price: String,
    @SerializedName("viewed")
    val viewed: Int,
    @SerializedName("star")
    val star: Int?,
    @SerializedName("bron_number")
    val bronNumber: String?,
    @SerializedName("room_number")
    val roomNumber: Int,
    @SerializedName("floor_number")
    val floorNumber: Int,
    @SerializedName("status")
    val status: String,
    @SerializedName("luxe")
    val luxe: Int,
    @SerializedName("luxe_status")
    val luxeStatus: Boolean,
    @SerializedName("luxe_expire")
    val luxeExpire: String?,
    @SerializedName("vip_status")
    val vipStatus: Boolean,
    @SerializedName("vip_expire")
    val vipExpire: String?,
    @SerializedName("images")
    val images: List<Image>,
    @SerializedName("possibilities")
    val possibilities: List<Option>,
    @SerializedName("comment")
    val comment: Int,
    @SerializedName("comment_count")
    val commentCount: Int,
    @SerializedName("comments")
    val comments: List<Comment>,
    @SerializedName("is_comment")
    val isComment: Boolean?,
    @SerializedName("who")
    val who: String?,
    @SerializedName("area")
    val area: String?,
    @SerializedName("exclusisive")
    val exclusisive: Int,
    @SerializedName("hashtag")
    val hashtag: String?,
    @SerializedName("level_number")
    val levelNumber: Int?,
    @SerializedName("liked")
    val liked: Boolean,
    @SerializedName("favorited")
    var favorite: Boolean,
    @SerializedName("shop")
    val shop: BusinessProfile?,
    @SerializedName("type")
    val type: String,
    @SerializedName("updated_at")
    val updatedAt: String,
    @SerializedName("created_at")
    val createdAt: String,
    @SerializedName("banner")
    val banner: String?
)