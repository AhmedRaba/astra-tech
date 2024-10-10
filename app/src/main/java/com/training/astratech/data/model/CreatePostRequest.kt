package com.training.astratech.data.model

import java.io.File

data class CreatePostRequest(
    val postTitle: String,
    val postMessage: String,
    val postImage: File
)
