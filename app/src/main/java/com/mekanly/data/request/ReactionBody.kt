package com.mekanly.data.request

import com.google.gson.annotations.SerializedName

class ReactionBody(
    @SerializedName("favoritable_id")
    val id: Int,
    @SerializedName("favoritable_type")
    val type: String,

)