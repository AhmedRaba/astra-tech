package com.training.astratech.ui.frags

import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.snackbar.Snackbar
import com.training.astratech.R
import com.training.astratech.data.model.CreatePostRequest
import com.training.astratech.databinding.FragmentAddPostDialogBinding
import com.training.astratech.ui.view_model.PostViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.io.File
import java.io.FileOutputStream

@AndroidEntryPoint
class AddPostDialogFragment : BottomSheetDialogFragment() {


    private lateinit var binding: FragmentAddPostDialogBinding
    private val viewModel: PostViewModel by viewModels()
    private lateinit var getImageLauncher: ActivityResultLauncher<PickVisualMediaRequest>
    private var selectedImageFile: File? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getImageLauncher =
            registerForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
                if (uri != null) {
                    Glide.with(this).load(uri).into(binding.ivAddPostImage)
                    selectedImageFile = getFileFromUri(uri)
                } else {
                    showSnackBar("No image selected")
                }
            }
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentAddPostDialogBinding.inflate(inflater, container, false)



        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        openGallery()
        validateInputs()

    }


    private fun validateInputs() {
        binding.btnAddPost.setOnClickListener {
            val postTitle = binding.textInputAddPostTitle.editText?.text.toString().trim()
            val postMessage = binding.textInputAddPostMessage.editText?.text.toString().trim()

            var hasError = false

            if (postTitle.isEmpty()) {
                binding.textInputAddPostTitle.error = "Post title cannot be empty"
                hasError = true
            } else {
                binding.textInputAddPostMessage.error = null
                hasError = false
            }
            if (postMessage.isEmpty()) {
                binding.textInputAddPostMessage.error = "Post title cannot be empty"
                hasError = true
            } else {
                binding.textInputAddPostMessage.error = null
                hasError = false
            }

            if (!hasError) {
                createPost(postTitle, postMessage)
            }
        }

    }

    private fun createPost(postTitle: String, postMessage: String) {

        val finalImage: File = selectedImageFile ?: run {
            val drawable = ContextCompat.getDrawable(requireContext(), R.drawable.ic_user)

            val bitmap = (drawable as BitmapDrawable).bitmap
            val defaultImageFile = File(
                requireContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES),
                "default_image.png"
            )

            FileOutputStream(defaultImageFile).use { output ->
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, output)
            }
            defaultImageFile
        }


        val post = CreatePostRequest(postTitle, postMessage, finalImage)

        viewModel.createPost(post)

        viewModel.refreshPosts()

        dismiss()

    }


    private fun openGallery() {
        binding.icAddPostImage.setOnClickListener {
            getImageLauncher.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
        }
    }

    private fun getFileFromUri(uri: Uri): File? {
        val filePathColumn = arrayOf(MediaStore.Images.Media.DATA)
        val cursor = requireContext().contentResolver.query(uri, filePathColumn, null, null, null)
        cursor?.moveToFirst()
        val columnIndex = cursor?.getColumnIndex(filePathColumn[0])
        val filePath = cursor?.getString(columnIndex!!)
        cursor?.close()
        return filePath?.let { File(it) }
    }

    private fun showSnackBar(message: String) {
        Snackbar.make(binding.root, message, Snackbar.LENGTH_SHORT).show()
    }

}