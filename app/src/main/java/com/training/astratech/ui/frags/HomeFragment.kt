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
import com.training.astratech.ui.view_model.PostState
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
        navToAddFragment()

    }

    private fun setupRecyclerView() {
        binding.rvPost.adapter = PostAdapter(emptyList()) {}
    }


    private fun observeViewModels() {
        viewModel.postsResponse.observe(viewLifecycleOwner) {
            when (it) {
                is PostState.Success -> {
                    Log.e("+++++++", "++++++++: ", )
                    showLoading(false)
                    updatePostsList(it.data)
                }

                is PostState.Error -> {
                    showSnackBar(it.message.toString())
                    showLoading(false)
                }

                PostState.Loading -> showLoading(true)
            }
        }



        viewModel.error.observe(viewLifecycleOwner) {
            Log.e("HomeFragment", "observeViewModels: $it")
            showSnackBar(it.toString())
        }
    }

     fun fetchPosts() {
        viewModel.fetchPosts()
    }

    private fun updatePostsList(posts: List<PostResponseItem>) {
        val adapter = PostAdapter(posts) {
            val action = HomeFragmentDirections.actionHomeFragmentToPostDetailsFragment(it)
            findNavController().navigate(action)
        }
        binding.rvPost.adapter = adapter
    }


    private fun navToAddFragment() {
        binding.fabAddPost.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_addPostDialogFragment)
        }
    }


    private fun showLoading(shouldLoad: Boolean) {
        if (shouldLoad) {

            binding.progressBarHome.visibility = View.VISIBLE
            binding.rvPost.visibility = View.GONE
        } else {
            binding.progressBarHome.visibility = View.GONE
            binding.rvPost.visibility = View.VISIBLE
        }

    }


    private fun showSnackBar(message: String) {
        Snackbar.make(binding.root, message, Snackbar.LENGTH_SHORT).show()
    }

}