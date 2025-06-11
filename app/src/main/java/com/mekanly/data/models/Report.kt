package com.mekanly.data.models

data class Report(

    val id: Int,
    val description: String,
    val type: String,
    val priority: String?, // может быть null
    val created_at: String,
    val updated_at: String

)
