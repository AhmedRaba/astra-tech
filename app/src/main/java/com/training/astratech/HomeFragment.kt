package com.training.astratech

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.training.astratech.data.model.PostResponseItem
import com.training.astratech.databinding.FragmentHomeBinding
import com.training.astratech.ui.adapter.PostAdapter
import com.training.astratech.ui.view_model.PostViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private val viewModel: PostViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)


        setupRecyclerView()
        observeViewModels()
        fetchPosts()



        return binding.root
    }

    private fun setupRecyclerView() {
        binding.rvPost.adapter = PostAdapter(emptyList())
    }


    private fun observeViewModels() {
        viewModel.posts.observe(viewLifecycleOwner) {
            updatePostsList(it)
            Log.e("HomeFragment", "observeViewModels: $it", )
        }
    }

    private fun fetchPosts() {
        viewModel.fetchPosts()
    }

    private fun updatePostsList(posts: List<PostResponseItem>) {
        val adapter = PostAdapter(posts)
        binding.rvPost.adapter = adapter
    }


}