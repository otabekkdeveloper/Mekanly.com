package com.mekanly.data.responseBody

data class RequestBodyDeviceInfo(
    val deviceToken: String,
    val deviceType: String = "android"
)