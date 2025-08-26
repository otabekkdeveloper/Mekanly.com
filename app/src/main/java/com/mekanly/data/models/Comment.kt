package com.mekanly.data.models

import com.google.gson.annotations.SerializedName

data class Comment(
    @SerializedName("id")
    val id: Long,

    @SerializedName("comment")
    val comment: String,

    @SerializedName("status")
    val status: String,

    @SerializedName("user_id")
    val userId: Int,

    @SerializedName("is_owner")
    val isOwner: Boolean,

    @SerializedName("commentable_id")
    val commentableId: Long,

    @SerializedName("commentable_type")
    val commentableType: String,

    @SerializedName("created_at")
    val createdAt: String,

    @SerializedName("parent_id")
    val parentId: Int?,

    @SerializedName("like")
    val like: Int,

    @SerializedName("dislike")
    val dislike: Int,

    @SerializedName("user_reaction")
    val userReaction: String?,

    @SerializedName("user")
    val user: UserName,

    @SerializedName("replies")
    val replies: List<Comment>
)

data class UserName(
    @SerializedName("id")
    val id: Int,

    @SerializedName("username")
    val username: String
)
