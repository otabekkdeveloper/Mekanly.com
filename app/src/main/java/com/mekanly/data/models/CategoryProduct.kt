package com.mekanly.data.models

import com.google.gson.annotations.SerializedName


data class CategoryProduct(
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("comment_count")
    val commentCount: Int,
    @SerializedName("user_id")
    val userId: Int,
    @SerializedName("images")
    val images: List<ProductImage>,
    @SerializedName("user")
    val user: User,
    @SerializedName("shop")
    val shop: Shop,
    @SerializedName("favorite")
    var favorite: Boolean,
    @SerializedName("type")
    val type: String,
    @SerializedName("location")
    val location: Location,
    @SerializedName("category")
    val category: Category,
    @SerializedName("shop_id")
    val shopId: Int,
    @SerializedName("location_id")
    val locationId: Int,
    @SerializedName("category_id")
    val categoryId: Int,
    @SerializedName("price")
    val price: Double,
    @SerializedName("status")
    val status: String,
    @SerializedName("expire")
    val expire: String,
    @SerializedName("phone")
    val phone: String,
    @SerializedName("who")
    val who: String,
    @SerializedName("delivery")
    val delivery: Boolean,
    @SerializedName("comment")
    val comment: Boolean,
    @SerializedName("description")
    val description: String,
    @SerializedName("brief_description")
    val briefDescription: String,
    @SerializedName("lover_price")
    val loverPrice: Int,
    @SerializedName("vip")
    val vip: Boolean,
    @SerializedName("exclusive")
    val exclusive: Boolean,
    @SerializedName("hashtag")
    val hashtag: String,
    @SerializedName("created_at")
    val createdAt: String,
    @SerializedName("updated_at")
    val updatedAt: String,
    @SerializedName("reason")
    val reason: String?
)
