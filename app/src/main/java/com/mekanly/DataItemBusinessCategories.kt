package com.mekanly

// Модель данных
data class DataItemBusinessCategories(
    val title: String? = null,
    val count: String? = null,
    val imageResId: Int? = null,
    val id: Int? = null,
    val type: BusinessType

)

// Перечисление типов бизнеса
enum class BusinessType {
    REAL_ESTATE, FURNITURE, HOUSEHOLD_APPLIANCES, CARPET_AND_RUGS, CHANDELIERS, CONSTRUCTION


}
