package com.mekanly.data.requestBody

import javax.security.auth.callback.PasswordCallback

data class RequestBodyRegister(
    val username:String,val phone:String, val password: String
)