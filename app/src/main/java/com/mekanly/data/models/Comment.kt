package com.mekanly.data.models

import com.google.gson.annotations.SerializedName

data class Comment(
    @SerializedName("username")
    val userName: String,
    @SerializedName("created_at")
    val date: String,
    @SerializedName("comment")
    val text: String,
    @SerializedName("like")
    val likeCount: Int,
    @SerializedName("dislike")
    val dislikeCount: Int
)