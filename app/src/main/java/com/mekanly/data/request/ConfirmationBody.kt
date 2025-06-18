package com.mekanly.data.request

data class ConfirmationBody (
    val phone:String,
    val token:String,
    val code:String
)