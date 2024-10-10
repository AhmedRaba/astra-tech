package com.training.astratech.ui.frags

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.training.astratech.R
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

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        observeViewModels()
        fetchPosts()
        addPost()


    }

    private fun setupRecyclerView() {
        binding.rvPost.adapter = PostAdapter(emptyList()) {}
    }


    private fun observeViewModels() {
        viewModel.posts.observe(viewLifecycleOwner) {
            updatePostsList(it)
        }

        viewModel.error.observe(viewLifecycleOwner) {
            Log.e("HomeFragment", "observeViewModels: $it")
            showSnackBar(it)
        }
    }

    private fun fetchPosts() {
        viewModel.fetchPosts()
    }

    private fun updatePostsList(posts: List<PostResponseItem>) {
        val adapter = PostAdapter(posts) {
            val action =
                HomeFragmentDirections.actionHomeFragmentToPostDetailsFragment(it)
            findNavController().navigate(action)
        }
        binding.rvPost.adapter = adapter
    }


    private fun addPost() {
        binding.fabAddPost.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_addPostDialogFragment)
        }
    }


    private fun showSnackBar(message: String) {
        Snackbar.make(binding.root, message, Snackbar.LENGTH_SHORT).show()
    }

}