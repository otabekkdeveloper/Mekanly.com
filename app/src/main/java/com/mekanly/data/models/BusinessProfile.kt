package com.mekanly.data.models

import com.google.gson.annotations.SerializedName
import java.util.Date

data class BusinessProfile(
    @SerializedName("id") val id: Int,
    @SerializedName("brand") val brand: String,
    @SerializedName("logo") val logo: String?,
    @SerializedName("image") val image: String?,
    @SerializedName("status") val status: String,
//    @SerializedName("description") val description: String?,
    @SerializedName("location_id") val locationId: Int,
    @SerializedName("expire") val expire: String?,
    @SerializedName("views") val views: Int,
    @SerializedName("cover_media") val coverMedia: String?,
    @SerializedName("rating") val rating: Int,
    @SerializedName("locations") val locations: String?,
    @SerializedName("phone_numbers") val phoneNumbers: String,
    @SerializedName("is_vip") val isVip: Int,
    @SerializedName("vip_days") val vipDays: Int?,
    @SerializedName("site") val site: String?,
    @SerializedName("messengers") val messengers: String?,
    @SerializedName("mail") val mail: String?,
    @SerializedName("created_at") val createdAt: Date,
    @SerializedName("updated_at") val updatedAt: Date
)
