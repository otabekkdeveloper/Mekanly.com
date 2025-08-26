package com.mekanly.data.models

import com.google.gson.annotations.SerializedName

data class Reaction(
    @SerializedName("likes_count") val likeCount: Int,
    @SerializedName("dislikes_count") val dislikeCount: Int,
    @SerializedName("user_reaction") val userReaction: Int,
)