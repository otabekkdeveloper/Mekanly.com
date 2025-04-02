package com.mekanly.data.responseBody

import com.mekanly.data.dataModels.DataUser

data class ResponseLogin(
    val message: String,
    val user: DataUser
)
