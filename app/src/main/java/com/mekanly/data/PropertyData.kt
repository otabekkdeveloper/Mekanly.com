package com.mekanly.data

import android.annotation.SuppressLint
import com.mekanly.data.models.Image
import com.mekanly.data.models.Location
import com.mekanly.data.models.Option
import org.w3c.dom.Comment

data class PropertyData @SuppressLint("RestrictedApi") constructor(
    val id: Int,
    val category_id: Int,
    val category_name: String,
    val location: Location,
    val enter_time: String?,
    val leave_time: String?,
    val day_enter_time: String?,
    val day_leave_time: String?,
    val name: String,
    val description: String?,
    val reason: String?,
    val viewed: Int,
    val room_number: Int,
    val floor_number: Int,
    val guest_number: Int,
    val order: String?,
    val price: String,
    val status: String,
    val luxe: Int,
    val bron_number: String?,
    val bron_status: Int,
    val user: Any,
    val images: List<Image>,
    val possibilities: List<Option>,
    val comments: List<Comment>,
    val updated_at: String,
    val created_at: String,
    val is_comment: Int
)

