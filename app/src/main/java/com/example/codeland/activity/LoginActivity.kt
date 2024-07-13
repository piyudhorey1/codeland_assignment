package com.example.codeland.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import androidx.appcompat.content.res.AppCompatResources
import com.example.codeland.R
import com.example.codeland.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {

    lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.ButtonLogin.setOnClickListener {
            if (validateField()) {
                val intent = Intent(this, ImageUploadActivity::class.java)
                startActivity(intent)
            }
        }
    }


    private fun validateField(): Boolean {
        var isValid = true

        if (TextUtils.isEmpty(binding.EditUsername.text)) {
            binding.EditUsername.background = AppCompatResources.getDrawable(
                this,
                R.drawable.background_edittext_corner_red_22
            )
            isValid = false
        }

        else {
            binding.EditUsername.background = AppCompatResources.getDrawable(
                this,
                R.drawable.background_edittext_corner_black_22
            )
        }

        if (TextUtils.isEmpty(binding.EditPassword.text)) {
            binding.EditPassword.background = AppCompatResources.getDrawable(
                this,
                R.drawable.background_edittext_corner_red_22
            )
            isValid = false
        }
        else {
            binding.EditPassword.background = AppCompatResources.getDrawable(
                this,
                R.drawable.background_edittext_corner_black_22
            )
        }

        return isValid
    }

}