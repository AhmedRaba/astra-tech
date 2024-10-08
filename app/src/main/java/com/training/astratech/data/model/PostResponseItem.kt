package com.training.astratech.data.model


import com.google.gson.annotations.SerializedName

data class PostResponseItem(
    @SerializedName("id")
    val id: Int,
    @SerializedName("post_image")
    val postImage: String,
    @SerializedName("post_message")
    val postMessage: String,
    @SerializedName("post_title")
    val postTitle: String
)