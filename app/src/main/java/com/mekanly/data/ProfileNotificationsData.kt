package com.mekanly.data



data class ProfileNotificationsData(
    val id: String,
    val title: String,
    val address: String,
    val time: String,
    val price: String,
    val status: String,
    val images: List<String>,
    var isFavorite: Boolean = false
)