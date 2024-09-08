package com.example.android.chefapp.ui

import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.view.children
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.android.chefapp.R
import com.example.android.chefapp.databinding.ActivityLoginBinding
import com.example.android.chefapp.viewmodel.LoginViewModel

class LoginActivity : AppCompatActivity() {
    private val viewModel: LoginViewModel by lazy {
        val activity = requireNotNull(this)
        ViewModelProvider(
            this,
            LoginViewModel.Factory(activity.application)
        )[LoginViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val binding: ActivityLoginBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_login)

        binding.viewModel = viewModel

        viewModel.password.observe(this) { newPassword ->
            var childrenCount = 1
            binding.pinList.children.forEach { child ->
                if (child is AppCompatImageView){
                    if (childrenCount <= newPassword.length) {
                        child.setImageResource(R.drawable.pin_circle_active)
                    }
                }
                childrenCount++
            }
            if (newPassword.length == 6) {
                viewModel.login()
            }
        }

        binding.lifecycleOwner = this
    }
}