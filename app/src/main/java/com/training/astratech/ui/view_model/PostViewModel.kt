package com.training.astratech.ui.view_model

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.training.astratech.data.model.PostResponseItem
import com.training.astratech.data.model.PostUpdateRequest
import com.training.astratech.data.repos.PostRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PostViewModel @Inject constructor(private val postRepository: PostRepository) : ViewModel() {

    private val _posts = MutableLiveData<List<PostResponseItem>>()
    val posts: LiveData<List<PostResponseItem>> = _posts

    private val _updatePostResponse = MutableLiveData<String>()
    val updateResponseItem: LiveData<String> = _updatePostResponse

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error

    fun fetchPosts() {
        viewModelScope.launch(Dispatchers.IO) {
            try {

                val response = postRepository.getPosts()
                if (response.isSuccessful) {
                    _posts.postValue(response.body())
                } else {
                    _error.postValue(response.errorBody().toString())
                    Log.e(
                        "PostViewModel",
                        "fetchPosts: ${_error.postValue(response.errorBody().toString())}"
                    )
                }

            } catch (e: Exception) {
                _error.postValue(e.message)
                Log.e("PostViewModel", "fetchPosts: ${_error.postValue(e.message)}")
            }
        }
    }


    fun updatePost(postUpdateRequest: PostUpdateRequest) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = postRepository.updatePost(postUpdateRequest)
                if (response.isSuccessful) {
                    _updatePostResponse.postValue(response.body())
                } else {
                    _error.postValue(response.errorBody()?.string())
                }

            } catch (e: Exception) {
                Log.e("PostViewModel", "updatePost: $e")
            }
        }
    }


    fun deletePost(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = postRepository.deletePost(id)
                if (response.isSuccessful) {
                    _updatePostResponse.postValue(response.body())
                } else {
                    _error.postValue(response.code().toString())
                }
            } catch (e: Exception) {
                Log.e("PostViewModel", "deletePost: $e")
            }

        }
    }

}
