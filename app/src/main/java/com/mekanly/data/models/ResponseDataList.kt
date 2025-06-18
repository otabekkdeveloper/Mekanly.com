package com.mekanly.data.models


data class ResponseDataList<T>(
    val data: List<T>
)

data class ResponseData<T>(
    val data: T
)
