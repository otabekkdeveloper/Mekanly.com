package com.mekanly.data.request

import com.google.gson.annotations.SerializedName

data class AddCommentBody(
    @SerializedName("comment")
    val comment: String,
    @SerializedName("commentable_id")
    val postId: Long,
    @SerializedName("comment_type")
    val type: String,
    @SerializedName("parent_id")
    val parentId: Long? = null
)