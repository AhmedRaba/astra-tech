package com.training.astratech

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import com.training.astratech.databinding.ActivityMainBinding
import com.training.astratech.ui.frags.HomeFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


    }

    fun refreshPostList() {
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        val currentFragment = navHostFragment.childFragmentManager.fragments.firstOrNull()

        if (currentFragment is HomeFragment) {
            currentFragment.fetchPosts()
        } else {
            Log.e("Main", "HomeFragment not found")
        }
    }
}