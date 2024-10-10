package com.training.astratech.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.training.astratech.data.model.PostResponseItem
import com.training.astratech.databinding.ItemPostBinding

class PostAdapter(
    private val posts: List<PostResponseItem>,
    val onClickListener: (PostResponseItem) -> Unit,
) :
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

            Glide.with(root.context)
                .load(currentPost.postImage)
                .transform(CircleCrop())
                .into(ivPost)


            root.setOnClickListener {
                onClickListener(currentPost)
            }


        }
    }


}