package com.training.astratech.ui.view_model

sealed class PostState<out T> {

    object Loading : PostState<Nothing>()

    data class Success<out T>(val data: T) : PostState<T>()

    data class Error(val message: String?) : PostState<Nothing>()


}