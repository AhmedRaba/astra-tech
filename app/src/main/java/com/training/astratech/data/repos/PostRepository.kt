package com.training.astratech.data.repos

import com.training.astratech.data.model.PostResponse
import com.training.astratech.data.model.PostUpdateRequest
import retrofit2.Response

interface PostRepository {

    suspend fun getPosts(): Response<PostResponse>

    suspend fun updatePost(postUpdateRequest: PostUpdateRequest): Response<String>

    suspend fun deletePost(id: Int): Response<String>

}