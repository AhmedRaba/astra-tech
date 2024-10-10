package com.training.astratech.data.data_source.remote

import com.training.astratech.data.api.ApiService
import com.training.astratech.data.model.DeletePostRequest
import com.training.astratech.data.model.PostResponse
import com.training.astratech.data.model.PostUpdateRequest
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
        postUpdateRequest: PostUpdateRequest,
    ): Response<String> {

        val idRequestBody =
            RequestBody.create(MediaType.parse("text/plain"), postUpdateRequest.id.toString())

        val titleRequestBody =
            RequestBody.create(MediaType.parse("text/plain"), postUpdateRequest.postTitle)
        val messageRequestBody =
            RequestBody.create(MediaType.parse("text/plain"), postUpdateRequest.postMessage)

        val requestFile =
            RequestBody.create(MediaType.parse("image/*"), postUpdateRequest.postImage)

        val imagePart = MultipartBody.Part.createFormData(
            "post_image",
            postUpdateRequest.postImage.name,
            requestFile
        )

        val response =
            apiService.updatePost(idRequestBody, titleRequestBody, messageRequestBody, imagePart)
        return response
    }


    suspend fun deletePost(id: Int): Response<String> {
        return apiService.deletePost(DeletePostRequest(id))
    }


}