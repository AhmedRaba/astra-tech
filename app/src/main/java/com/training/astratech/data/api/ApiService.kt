package com.training.astratech.data.api

import com.training.astratech.data.model.PostDeleteRequest
import com.training.astratech.data.model.PostResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface ApiService {

    @GET("getposts")
    suspend fun getPosts(): Response<PostResponse>


    @POST("updatepost")
    @Multipart
    suspend fun updatePost(
        @Part("id") postId: RequestBody,
        @Part("post_title") postTitle: RequestBody,
        @Part("post_message") postMessage: RequestBody,
        @Part postImage: MultipartBody.Part,
    ): Response<String>


    @POST("deletepost")
    suspend fun deletePost(
        @Body request: PostDeleteRequest
    ): Response<String>


}