package com.mekanly.data.models

data class ShopProduct(
    val id: Int,
    val name: String,
    val comment_count: Int,
    val user_id: Int,
    val images: List<Image>,
    val user: User,
    val shop: Shop,
    var favorite: Boolean,
    val type: String,
    val location: Location,
    val category: Category,
    val shop_id: Int,
    val location_id: Int,
    val category_id: Int,
    val price: Double,
    val status: String,
    val expire: String,
    val phone: String,
    val who: String,
    val delivery: Boolean,
    val description: String,
    val brief_description: String,
    val lover_price: Int,
    val vip: Boolean,
    val exclusive: Boolean,
    val hashtag: String,
    val created_at: String,
    val updated_at: String,
    val reason: String?
)


data class Shop(
    val id: Int,
    val brand: String,
    val logo: String,
    val image: String,
    val description: String?,
    val brief_description: String?,
    val views: Int,
    val cover_media: String,
    val phone_numbers: List<String>?,
    val vip_status: Boolean,
    val site: String,
    val mail: String,
    val social_profiles: List<SocialProfile>,
    val location: List<String>,
    val product_count: Int,
    val product_categories: List<ProductCategory>
)

data class SocialProfile(
    val sociable_id: Int,
    val platform: String,
    val url: String
)

data class ProductCategory(
    val id: Int,
    val title: String?,
    val image: String?,
    val parent: ParentCategory?,
    val description: String?,
    val products_count: Int,
    val type: String,
    val parent_id: Int? = null,
    val name: String? = null,
    val created_at: String? = null,
    val updated_at: String? = null
)

data class ParentCategory(
    val id: Int,
    val title: String,
    val image: String,
    val parent: Any?,
    val description: String?
)



data class Category(
    val id: Int,
    val title: String,
    val image: String,
    val parent: String,
    val description: String?
)
