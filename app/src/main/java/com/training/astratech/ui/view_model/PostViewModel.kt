package com.training.astratech.ui.view_model

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.training.astratech.data.model.PostResponseItem
import com.training.astratech.domain.model.CreatePostRequest
import com.training.astratech.domain.model.UpdatePostRequest
import com.training.astratech.domain.repos.PostRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PostViewModel @Inject constructor(private val postRepository: PostRepository) : ViewModel() {

    private val _postsResponse = MutableLiveData<PostState<List<PostResponseItem>>>()
    val postsResponse: LiveData<PostState<List<PostResponseItem>>> = _postsResponse

    private val _createPostResponse = MutableLiveData<PostState<String>>()
    val createPostResponse: LiveData<PostState<String>> = _createPostResponse

    private val _updatePostResponse = MutableLiveData<PostState<String>>()
    val updatePostResponse: LiveData<PostState<String>> = _updatePostResponse

    private val _deletePostResponse = MutableLiveData<PostState<String>>()
    val deletePostResponse: LiveData<PostState<String>> = _deletePostResponse

    private val _error = MutableLiveData<PostState<String>>()
    val error: LiveData<PostState<String>> = _error


    fun fetchPosts() {
        _postsResponse.postValue(PostState.Loading)
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = postRepository.getPosts()
                if (response.isSuccessful) {
                    response.body()?.let {
                        _postsResponse.postValue(PostState.Success(it))
                    }
                } else {
                    _postsResponse.postValue(PostState.Error(response.errorBody()?.string()))
                }

            } catch (e: Exception) {
                _error.postValue(PostState.Error(e.message))
                Log.e("PostViewModel", "fetchPosts: $e")
            }
        }
    }


    fun createPost(createPostRequest: CreatePostRequest) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = postRepository.createPost(createPostRequest)
                if (response.isSuccessful) {
                    response.body()?.let {
                        _createPostResponse.postValue(PostState.Success(it))
                    }
                } else {
                    _error.postValue(PostState.Error(response.errorBody()?.string()))
                }

            } catch (e: Exception) {
                _error.postValue(PostState.Error(e.message))
                Log.e("PostViewModel", "createPost: $e")
            }
        }
    }


    fun updatePost(updatePostRequest: UpdatePostRequest) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = postRepository.updatePost(updatePostRequest)
                if (response.isSuccessful) {
                    response.body()?.let {
                        _updatePostResponse.postValue(PostState.Success(it))
                    }
                } else {
                    _error.postValue(PostState.Error(response.errorBody()?.string()))
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
                    response.body()?.let {
                        _deletePostResponse.postValue(PostState.Success(it))
                    }
                } else {
                    _error.postValue(PostState.Error(response.errorBody()?.string()))
                }
            } catch (e: Exception) {
                Log.e("PostViewModel", "deletePost: $e")
            }

        }
    }

}
