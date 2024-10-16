package com.training.astratech.domain.model

import java.io.File

data class UpdatePostRequest(
    val id: Int,
    val postTitle: String,
    val postMessage: String,
    val postImage: File
)
