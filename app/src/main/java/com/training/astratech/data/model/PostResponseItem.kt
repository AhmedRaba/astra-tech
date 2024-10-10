package com.training.astratech.data.model


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize


@Parcelize
data class PostResponseItem(
    @SerializedName("id")
    val id: Int,
    @SerializedName("post_image")
    val postImage: String,
    @SerializedName("post_message")
    val postMessage: String,
    @SerializedName("post_title")
    val postTitle: String
): Parcelable