package com.training.astratech.data.repos

import com.training.astratech.data.data_source.remote.PostRemoteDataSource
import com.training.astratech.data.model.CreatePostRequest
import com.training.astratech.data.model.PostResponse
import com.training.astratech.data.model.UpdatePostRequest
import retrofit2.Response
import javax.inject.Inject

class PostRepositoryImpl @Inject constructor(private val postRemoteDataSource: PostRemoteDataSource) :
    PostRepository {

    override suspend fun getPosts(): Response<PostResponse> {
        return postRemoteDataSource.getPosts()
    }

    override suspend fun updatePost(updatePostRequest: UpdatePostRequest): Response<String> {
        return postRemoteDataSource.updatePost(updatePostRequest)
    }

    override suspend fun createPost(createPostRequest: CreatePostRequest): Response<String> {
        return postRemoteDataSource.createPost(createPostRequest)
    }

    override suspend fun deletePost(id: Int): Response<String> {
        return postRemoteDataSource.deletePost(id)
    }


}