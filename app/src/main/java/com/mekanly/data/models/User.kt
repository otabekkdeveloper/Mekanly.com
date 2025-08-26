package com.mekanly.data.models

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

data class User(
    @SerializedName("id") val id: Int,
    @SerializedName("username") val username: String,
    @SerializedName("token") val token: String,
    @SerializedName("email") val email: String?,
    @SerializedName("phone") val phone: String,
    @SerializedName("vip") val vip: Int,
    @SerializedName("email_verified_at") val emailVerifiedAt: String?,
    @SerializedName("phone_verified_at") val phoneVerifiedAt: String?,
    @SerializedName("role") val role: String,
    @SerializedName("status") val status: String
)