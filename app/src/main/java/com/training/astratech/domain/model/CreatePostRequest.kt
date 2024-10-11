package com.training.astratech.domain.model

import java.io.File

data class CreatePostRequest(
    val postTitle: String,
    val postMessage: String,
    val postImage: File
)
