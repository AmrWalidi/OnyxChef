package com.example.android.chefapp.ui

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.view.children
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.android.chefapp.R
import com.example.android.chefapp.databinding.ApplicationSettingsPanelBinding
import com.example.android.chefapp.databinding.FragmentLoginBinding
import com.example.android.chefapp.viewmodel.LoginViewModel
import java.util.Locale

class LoginFragment : Fragment() {
    private val viewModel: LoginViewModel by lazy {
        val activity = requireNotNull(this.activity)
        ViewModelProvider(
            this,
            LoginViewModel.Factory(activity.application)
        )[LoginViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val sharedPrefs = requireContext().getSharedPreferences("settings", Context.MODE_PRIVATE)
        val languageCode = sharedPrefs.getString("language_code", "en")
        if (languageCode != null) {
            val locale = Locale(languageCode)
            Locale.setDefault(locale)

            val resources = requireContext().resources
            val config = resources.configuration
            config.setLocale(locale)

            resources.updateConfiguration(config, resources.displayMetrics)
        }

        val binding: FragmentLoginBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_login, container, false)
        binding.viewModel = viewModel
        viewModel.password.observe(viewLifecycleOwner) { newPassword ->
            var childrenCount = 1
            binding.pinList.children.forEach { child ->
                if (child is AppCompatImageView) {
                    if (childrenCount <= newPassword.length) {
                        child.setImageResource(R.drawable.pin_circle_active)
                    } else {
                        child.setImageResource(R.drawable.pin_circle)
                    }
                }
                childrenCount++
            }
            if (newPassword.length == 6) {
                viewModel.login()
            }
        }
        viewModel.valid.observe(viewLifecycleOwner) {
            if (!it) {
                binding.errorMessage.text = getString(R.string.pin_error_message)
                binding.errorMessage.visibility = View.VISIBLE
            } else {
                viewModel.user.value?.let { user ->
                    findNavController().navigate(
                        LoginFragmentDirections.actionLoginFragmentToHomeFragment(user)
                    )
                }
            }
        }


        val settingPanel = getSettingDialog(viewModel)
        viewModel.setting.observe(viewLifecycleOwner) {
            if (it) {
                settingPanel.show()
            } else {
                settingPanel.dismiss()
            }
        }
        binding.lifecycleOwner = this
        return binding.root
    }

    private fun getSettingDialog(viewModel: LoginViewModel): Dialog {
        val dialog = Dialog(requireContext())

        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)

        val binding: ApplicationSettingsPanelBinding = DataBindingUtil.inflate(
            LayoutInflater.from(requireContext()),
            R.layout.application_settings_panel,
            null,
            false
        )

        dialog.setContentView(binding.root)

        dialog.window?.setLayout(
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )

        dialog.setCancelable(true)

        binding.viewModel = viewModel

        return dialog
    }
}