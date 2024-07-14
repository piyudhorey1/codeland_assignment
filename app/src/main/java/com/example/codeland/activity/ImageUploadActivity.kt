package com.example.codeland.activity

import android.Manifest
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.example.codeland.CodelandApplication
import com.example.codeland.R
import com.example.codeland.databinding.ActivityImageUploadBinding

class ImageUploadActivity : AppCompatActivity() {

    lateinit var binding: ActivityImageUploadBinding
    private lateinit var imageUri: Uri
    private val maxImageSize = 10 * 1024 * 1024

    private val readImagePermission = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU)
        Manifest.permission.READ_MEDIA_IMAGES
    else
        Manifest.permission.READ_EXTERNAL_STORAGE

    private val storagePermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (isGranted) {
                getContent()
            }
            else {
                Toast.makeText(this, "Permission denied. Cannot access external storage.", Toast.LENGTH_SHORT).show()
            }
        }

    private val contract = registerForActivityResult(ActivityResultContracts.GetContent()) {
        if (it == null) {
            onDestroy()
        }
        else if (isImageSizeWithinLimit(it)){
            imageUri = it
            CodelandApplication.prefHelper.putString("image_uri", imageUri.toString())
        }else{
            Toast.makeText(this, "Image size exceeds the limit of 10 MB", Toast.LENGTH_SHORT).show()
        }
    }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityImageUploadBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.ButtonUpload.setOnClickListener {
            startImagePicker()
        }
        binding.ButtonView.setOnClickListener {
            Glide
                .with(this)
                .load(CodelandApplication.prefHelper.getString("image_uri"))
                .centerCrop()
                .into(binding.ImageUploaded)
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        CodelandApplication.prefHelper.clear()
    }

    private fun getContent() {
        contract.launch("image/*")
    }

    private fun isImageSizeWithinLimit(uri: Uri): Boolean {
        val inputStream = this.contentResolver.openInputStream(uri)
        inputStream?.use { stream ->
            val sizeInBytes = stream.available()
            return sizeInBytes <= maxImageSize
        }
        return false
    }

    private fun startImagePicker() {
        if (ContextCompat.checkSelfPermission(this@ImageUploadActivity, readImagePermission) == PackageManager.PERMISSION_GRANTED) {
            getContent()
        } else {
            requestStoragePermission()
        }
    }

    private fun requestStoragePermission() {
        storagePermissionLauncher.launch(readImagePermission)
    }
}