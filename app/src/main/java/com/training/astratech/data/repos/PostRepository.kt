package com.training.astratech.data.repos

import com.training.astratech.data.model.PostResponse
import retrofit2.Response

interface PostRepository {

    suspend fun getPosts(): Response<PostResponse>



}