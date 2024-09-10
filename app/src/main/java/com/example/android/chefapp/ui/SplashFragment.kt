package com.example.android.chefapp.ui

import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.android.chefapp.R
import com.example.android.chefapp.viewmodel.LoginViewModel

class SplashFragment : Fragment() {

    private lateinit var viewModel: LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val activity = requireNotNull(this.activity)
        viewModel = ViewModelProvider(
            this, LoginViewModel.Factory(activity.application)
        )[LoginViewModel::class.java]

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Handler().postDelayed({
            if (viewModel.user.value == null)
                findNavController().navigate(SplashFragmentDirections.actionSplashFragmentToLoginFragment())
            else {
                viewModel.user.value?.let { user ->
                    findNavController().navigate(
                        SplashFragmentDirections.actionSplashFragmentToHomeFragment(
                            user
                        )
                    )
                }

            }
        }, 3000)
        return inflater.inflate(R.layout.fragment_splash, container, false)
    }

}