package com.training.astratech.ui.frags

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import com.training.astratech.data.model.PostUpdateRequest
import com.training.astratech.databinding.FragmentPostDetailsBinding
import com.training.astratech.ui.view_model.PostViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.io.File
import java.io.FileOutputStream

@AndroidEntryPoint
class PostDetailsFragment : Fragment() {

    private lateinit var binding: FragmentPostDetailsBinding
    private val args: PostDetailsFragmentArgs by navArgs()
    private val viewModel: PostViewModel by viewModels()
    private lateinit var getImageLauncher: ActivityResultLauncher<PickVisualMediaRequest>
    private var selectedImageFile: File? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getImageLauncher =
            registerForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
                if (uri != null) {
                    Glide.with(this).load(uri).into(binding.ivPost)
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
        binding = FragmentPostDetailsBinding.inflate(inflater, container, false)


        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.textInputPostTitle.editText?.setText(args.postItem.postTitle)
        binding.textInputPostMessage.editText?.setText(args.postItem.postMessage)
        Glide.with(this)
            .load(args.postItem.postImage)
            .into(binding.ivPost)

        binding.ivBack.setOnClickListener {
            findNavController().navigateUp()
        }

        openGallery()


        validateInputs()


        observeViewModels()

        deletePost()

    }


    private fun observeViewModels() {
        viewModel.updateResponseItem.observe(viewLifecycleOwner) {
            showSnackBar(it)
        }
        viewModel.error.observe(viewLifecycleOwner) {
            Log.e("observeViewModelsError", "observeViewModels: $it")
            showSnackBar(it)
        }

    }

    private fun openGallery() {
        getImageLauncher.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
    }

    private fun validateInputs() {
        binding.btnSave.setOnClickListener {
            val postTitle = binding.textInputPostTitle.editText?.text.toString().trim()
            val postMessage = binding.textInputPostMessage.editText?.text.toString().trim()

            var hasError = false

            if (postTitle.isEmpty()) {
                binding.textInputPostTitle.error = "Post title cannot be empty"
                hasError = true
            } else {
                binding.textInputPostTitle.error = null
                hasError = false
            }
            if (postMessage.isEmpty()) {
                binding.textInputPostMessage.error = "Post title cannot be empty"
                hasError = true
            } else {
                binding.textInputPostMessage.error = null
                hasError = false
            }

            if (!hasError) {
                updatePost(postTitle, postMessage)
            }
        }

    }

    private fun updatePost(postTitle: String, postMessage: String) {
        val finalImageFile = selectedImageFile ?: saveImageViewToFile(
            binding.ivPost, requireContext(), "update_post_image.png"
        )

        if (finalImageFile != null) {
            val postItem = PostUpdateRequest(
                args.postItem.id, postTitle, postMessage, finalImageFile
            )
            viewModel.updatePost(postItem)
            findNavController().navigateUp()
        } else {
            showSnackBar("Failed to save the image. Please select a new image or try again.")
        }


    }

    private fun deletePost() {
        binding.btnDelete.setOnClickListener {
            val id = args.postItem.id
            viewModel.deletePost(id)
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


    private fun saveImageViewToFile(
        imageView: ImageView,
        context: Context,
        fileName: String,
    ): File? {

        val drawable: Drawable? = imageView.drawable
        if (drawable is BitmapDrawable) {

            val bitmap: Bitmap = drawable.bitmap


            val file = File(context.getExternalFilesDir(Environment.DIRECTORY_PICTURES), fileName)


            return try {
                FileOutputStream(file).use { output ->
                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, output)
                }
                file
            } catch (e: Exception) {
                e.printStackTrace()
                null
            }
        }
        return null
    }
}
