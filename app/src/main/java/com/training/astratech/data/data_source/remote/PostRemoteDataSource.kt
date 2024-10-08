package com.training.astratech.data.data_source.remote

import android.util.Log
import com.training.astratech.data.api.ApiService
import com.training.astratech.data.model.PostResponse
import retrofit2.Response
import javax.inject.Inject

class PostRemoteDataSource @Inject constructor(private val apiService: ApiService) {

    suspend fun getPosts(): Response<PostResponse> {
        val response = apiService.getPosts()
        Log.e("PostRemoteDataSource", "getPosts: ${response.body()}")
        return response
    }


}