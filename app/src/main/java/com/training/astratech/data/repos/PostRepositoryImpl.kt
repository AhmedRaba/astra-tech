package com.training.astratech.data.repos

import com.training.astratech.data.data_source.remote.PostRemoteDataSource
import com.training.astratech.data.model.PostResponse
import com.training.astratech.data.model.PostResponseItem
import com.training.astratech.data.model.PostUpdateRequest
import retrofit2.Response
import javax.inject.Inject

class PostRepositoryImpl @Inject constructor(private val postRemoteDataSource: PostRemoteDataSource) :
    PostRepository {

    override suspend fun getPosts(): Response<PostResponse> {
        return postRemoteDataSource.getPosts()
    }

    override suspend fun updatePost(postUpdateRequest: PostUpdateRequest): Response<String> {
        return postRemoteDataSource.updatePost(postUpdateRequest)
    }

    override suspend fun deletePost(id: Int): Response<String> {
        return postRemoteDataSource.deletePost(id)
    }


}