package com.mekanly.data.models

import com.google.gson.annotations.SerializedName


data class ShopProduct(
    val id: Int,
    val name: String,
    @SerializedName("comment_count")
    val commentCount: Int,
    val price: Double,
    val status: String,
    var favorited: Boolean,
    val expire: String,
    val phone: String,
    val who: String,
    val delivery: Int,
    val comment: Int,
    val description: String,
    @SerializedName("lover_percentage")
    val loverPercentage: Int,
    @SerializedName("lover_price")
    val loverPrice: String,
    val vip: String? = null,
    val exclusive: Int,
    val hashtag: String,
    val images: List<ProductImage>,
    @SerializedName("created_at")
    val createdAt: String,
    val user: User,
    @SerializedName("user_id")
    val userId: Int,
    @SerializedName("user_name")
    val userName: String,
    @SerializedName("user_phone")
    val userPhone: String,
    @SerializedName("location_id")
    val locationId: Int,
    @SerializedName("location_name")
    val locationName: String,
    @SerializedName("location_parent")
    val locationParent: Location,
    @SerializedName("category_id")
    val categoryId: Int,
    @SerializedName("category_title")
    val categoryTitle: String,
    @SerializedName("category_parent")
    val categoryParent: String,
    val shop: Shop,
    @SerializedName("shop_id")
    val shopId: Int,
    @SerializedName("shop_brand")
    val shopBrand: String,
    @SerializedName("shop_logo")
    val shopLogo: String,
    val type: String,
    val reason: String? = null
)

data class ProductImage(
    val id: Int,
    val path: String,
    val original: String,
    val thumbnail: String,
    val watermark: String
)


data class Shop(
    val id: Int,
    val brand: String,
    val logo: String,
    val image: String,
    val description: String? = null,
    @SerializedName("brief_description")
    val briefDescription: String? = null,
    val views: Int,
    @SerializedName("cover_media")
    val coverMedia: String,
    @SerializedName("phone_numbers")
    val phoneNumbers: String? = null,
    @SerializedName("vip_status")
    val vipStatus: Boolean,
    val site: String,
    val mail: String,
    @SerializedName("social_profiles")
    val socialProfiles: List<SocialProfile>,
    val location: List<String>, // Empty array in JSON
    @SerializedName("product_count")
    val productCount: Int,
    @SerializedName("product_categories")
    val productCategories: List<ProductCategory>
)

data class SocialProfile(
    @SerializedName("sociable_id")
    val sociableId: Int,
    val platform: String,
    val url: String
)

data class ProductCategory(
    val id: Int,
    val title: String,
    val image: String? = null,
    val parent: CategoryParent? = null,
    val description: String? = null,
    @SerializedName("products_count")
    val productsCount: Int,
    val type: String,
    @SerializedName("parent_id")
    val parentId: Int? = null,
    val name: String? = null,
    @SerializedName("created_at")
    val createdAt: String? = null,
    @SerializedName("updated_at")
    val updatedAt: String? = null
)

data class CategoryParent(
    val id: Int,
    val title: String,
    val image: String,
    val parent: String? = null,
    val description: String? = null
)