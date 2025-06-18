package com.mekanly.data.models

import com.google.gson.annotations.SerializedName
import com.mekanly.presentation.ui.enums.BusinessType

data class BusinessCategory(
    val title: String = "",
    @SerializedName("business_profiles") val count: Int = 0,
    val image: String,
    val imageResId: Int? = null,
    val id: Int = 0,
    val type: BusinessType? = null,
    val subcategories: List<BusinessSubCategory>
)



