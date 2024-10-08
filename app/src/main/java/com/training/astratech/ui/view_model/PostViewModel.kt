package com.training.astratech.ui.view_model

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.training.astratech.data.model.PostResponseItem
import com.training.astratech.data.repos.PostRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PostViewModel @Inject constructor(private val postRepository: PostRepository) : ViewModel() {

    private val _posts = MutableLiveData<List<PostResponseItem>>()
    val posts: LiveData<List<PostResponseItem>> = _posts

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error

    fun fetchPosts() {
        try {
            viewModelScope.launch(Dispatchers.IO) {
                val response = postRepository.getPosts()
                if (response.isSuccessful) {
                    _posts.postValue(response.body())
                    Log.e("PostViewModel", "fetchPosts: ${response.body()}", )
                } else {
                    _error.postValue(response.errorBody().toString())
                    Log.e(
                        "PostViewModel",
                        "fetchPosts: ${_error.postValue(response.errorBody().toString())}"
                    )
                }
            }
        } catch (e: Exception) {
            _error.postValue(e.message)
            Log.e("PostViewModel", "fetchPosts: ${_error.postValue(e.message)}")
        }
    }
}