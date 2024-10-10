package com.training.astratech.data.model

import java.io.File

data class PostUpdateRequest(
    val id: Int,
    val postTitle: String,
    val postMessage: String,
    val postImage: File
)
