package com.training.astratech.data.data_source.remote

import com.training.astratech.data.api.ApiService
import com.training.astratech.domain.model.CreatePostRequest
import com.training.astratech.domain.model.DeletePostRequest
import com.training.astratech.data.model.PostResponse
import com.training.astratech.domain.model.UpdatePostRequest
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import javax.inject.Inject

class PostRemoteDataSource @Inject constructor(private val apiService: ApiService) {

    suspend fun getPosts(): Response<PostResponse> {
        val response = apiService.getPosts()
        return response
    }

    suspend fun updatePost(
        updatePostRequest: UpdatePostRequest,
    ): Response<String> {

        val idRequestBody =
            RequestBody.create(MediaType.parse("text/plain"), updatePostRequest.id.toString())

        val titleRequestBody =
            RequestBody.create(MediaType.parse("text/plain"), updatePostRequest.postTitle)
        val messageRequestBody =
            RequestBody.create(MediaType.parse("text/plain"), updatePostRequest.postMessage)

        val requestFile =
            RequestBody.create(MediaType.parse("image/*"), updatePostRequest.postImage)

        val imagePart = MultipartBody.Part.createFormData(
            "post_image",
            updatePostRequest.postImage.name,
            requestFile
        )

        val response =
            apiService.updatePost(idRequestBody, titleRequestBody, messageRequestBody, imagePart)
        return response
    }


    suspend fun createPost(createPostRequest: CreatePostRequest): Response<String> {
        val titleRequestBody =
            RequestBody.create(MediaType.parse("text/plain"), createPostRequest.postTitle)
        val messageRequestBody =
            RequestBody.create(MediaType.parse("text/plain"), createPostRequest.postMessage)

        val requestFile =
            RequestBody.create(MediaType.parse("image/*"), createPostRequest.postImage)

        val imagePart = MultipartBody.Part.createFormData(
            "post_image",
            createPostRequest.postImage.name,
            requestFile
        )

        val response = apiService.createPost(titleRequestBody, messageRequestBody, imagePart)
        return response
    }

    suspend fun deletePost(id: Int): Response<String> {
        return apiService.deletePost(DeletePostRequest(id))
    }


}