package com.mekanly.data.responseLocation

data class ApiResponse(
    val data: List<House>
)

data class House(
    val id: Int,
    val category_id: Int,
    val category_name: String,
    val location: Location,
    val name: String,
    val description: String,
    val enter_time: String,
    val leave_time: String,
    val viewed: Int,
    val room_number: Int,
    val floor_number: Int,
    val price: String,
    val day_enter_time: String,
    val day_leave_time: String,
    val status: String,
    val user: User,
    val images: List<Image>,
    val possibilities: List<Possibility>
)

data class Location(
    val id: Int,
    val parent_id: Int,
    val name: String,
    val parent_name: String
)

data class User(
    val id: Int,
    val username: String,
    val phone: String
)

data class Image(
    val id: Int,
    val url: String
)

data class Possibility(
    val id: Int,
    val name: String
)
