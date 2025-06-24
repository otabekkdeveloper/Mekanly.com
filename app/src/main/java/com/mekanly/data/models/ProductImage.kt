package com.mekanly.data.models

import com.google.gson.annotations.SerializedName

data class ProductImage(
@SerializedName("id")
val id: Int,
@SerializedName("path")
val path: String,
@SerializedName("original")
val original: String,
@SerializedName("thumbnail")
val thumbnail: String,
@SerializedName("watermark")
val watermark: String
)

