package com.training.astratech.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil3.load
import coil3.request.crossfade
import coil3.request.placeholder
import coil3.request.transformations
import coil3.transform.CircleCropTransformation
import com.training.astratech.R
import com.training.astratech.data.model.PostResponseItem
import com.training.astratech.databinding.ItemPostBinding

class PostAdapter(private val posts: List<PostResponseItem>) :
    RecyclerView.Adapter<PostAdapter.PostViewHolder>() {


    inner class PostViewHolder(val binding: ItemPostBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val binding = ItemPostBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PostViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return posts.size
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        val currentPost = posts[position]
        holder.binding.apply {

            tvPostTitle.text = currentPost.postTitle

            ivPost.load(currentPost.postImage) {
                transformations(CircleCropTransformation())
                placeholder(R.drawable.ic_launcher_background)
                crossfade(true)
                crossfade(1000)
            }


        }
    }


}