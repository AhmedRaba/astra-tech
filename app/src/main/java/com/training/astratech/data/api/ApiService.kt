package com.training.astratech.data.api

import com.training.astratech.data.model.PostResponse
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {

    @GET("getposts")
    suspend fun getPosts(): Response<PostResponse>

}