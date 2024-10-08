package com.training.astratech.data.repos

import com.training.astratech.data.data_source.remote.PostRemoteDataSource
import com.training.astratech.data.model.PostResponse
import retrofit2.Response
import javax.inject.Inject

class PostRepositoryImpl @Inject constructor(private val postRemoteDataSource: PostRemoteDataSource) : PostRepository {

    override suspend fun getPosts(): Response<PostResponse> {
        return postRemoteDataSource.getPosts()
    }

}