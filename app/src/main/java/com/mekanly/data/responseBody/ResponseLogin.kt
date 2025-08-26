package com.mekanly.data.responseBody

import com.mekanly.data.models.User

data class ResponseLogin(
    val message: String,
    val user: User
)
