package com.benyfrabasa.app.presentation.dashboard.photo

import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.benyfrabasa.app.databinding.FragmentPhotoBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class PhotoFragment : Fragment() {
    private val viewModel by viewModels<PhotoViewModel>()
    private var binding: FragmentPhotoBinding? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        binding = FragmentPhotoBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.CREATED) {
                viewModel.effect.collect { effect ->
                    when (effect) {
                        is PhotoViewEffect.OpenCamera -> {
                            handleCamera()
                        }
                    }
                }
            }
        }
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.CREATED) {
                viewModel.state.collect { state ->
                    renderState(state)
                }
            }
        }

        setListeners()
    }

    private fun setListeners() {
        binding?.apply {
            imagePhotoButton.setOnClickListener {
                viewModel.onEvent(PhotoEvent.TakePhoto)
            }
        }
    }

    private fun renderState(photoState: PhotoState) {
        binding?.apply {
            photoState.apply {
                openCameraLabel.isVisible = takePicture
                image?.let { imagePhotoButton.setImageBitmap(it) }
            }
        }
    }

    private fun handleCamera() {
        try {
            resultTakePhoto.launch(Intent(MediaStore.ACTION_IMAGE_CAPTURE))
        } catch (ex: ActivityNotFoundException) {
            Toast.makeText(
                requireContext(),
                NOT_CAMERA_ERROR,
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    private val resultTakePhoto =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val imageBitmap = result.data?.extras?.get("data") as Bitmap
                viewModel.onEvent(PhotoEvent.DrawPhoto(imageBitmap))
            }
        }

    companion object {
        private const val NOT_CAMERA_ERROR = "No camera supported"
    }
}
