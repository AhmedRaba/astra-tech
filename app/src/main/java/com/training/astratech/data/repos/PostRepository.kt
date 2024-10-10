package com.training.astratech.data.repos

import com.training.astratech.data.model.CreatePostRequest
import com.training.astratech.data.model.PostResponse
import com.training.astratech.data.model.UpdatePostRequest
import retrofit2.Response

interface PostRepository {

    suspend fun getPosts(): Response<PostResponse>

    suspend fun updatePost(updatePostRequest: UpdatePostRequest): Response<String>

    suspend fun createPost(createPostRequest: CreatePostRequest): Response<String>

    suspend fun deletePost(id: Int): Response<String>

}