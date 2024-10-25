package com.example.android.chefapp.ui

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.android.chefapp.R
import com.example.android.chefapp.databinding.FragmentHomeBinding
import com.example.android.chefapp.databinding.LanguageSelectionPanelBinding
import com.example.android.chefapp.databinding.LogoutPanelBinding
import com.example.android.chefapp.viewmodel.HomeViewModel
import java.util.Locale


class HomeFragment : Fragment() {

    private val viewModel: HomeViewModel by lazy {
        val activity = requireNotNull(this.activity)
        ViewModelProvider(
            this,
            HomeViewModel.Factory(
                activity.application,
                HomeFragmentArgs.fromBundle(requireArguments()).user
            )
        )[HomeViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val sharedPrefs = requireContext().getSharedPreferences("settings", Context.MODE_PRIVATE)
        val languageCode = sharedPrefs.getString("language_code", "en")
        if (languageCode != null) changeAppLanguage(languageCode)

        val binding: FragmentHomeBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)

        binding.viewModel = viewModel


        viewModel.fragmentNumber.observe(viewLifecycleOwner) { btn ->

            when (btn) {
                1 -> {
                    binding.ordersButton.background =
                        this.context?.let { ContextCompat.getDrawable(it, R.drawable.nav_clicked) }
                    binding.summaryButton.background = null
                    binding.historyButton.background = null
                }

                2 -> {
                    binding.historyButton.background =
                        this.context?.let { ContextCompat.getDrawable(it, R.drawable.nav_clicked) }
                    binding.summaryButton.background = null
                    binding.ordersButton.background = null
                }

                3 -> {
                    binding.summaryButton.background =
                        this.context?.let { ContextCompat.getDrawable(it, R.drawable.nav_clicked) }
                    binding.historyButton.background = null
                    binding.ordersButton.background = null
                }

                else -> return@observe
            }

        }

        viewModel.fragmentNumber.observe(viewLifecycleOwner) { num ->

            val bundle = Bundle().apply {
                viewModel.user.value?.let {
                    putInt("terminal", it.terminal)
                    putInt("branch", it.branch)
                }
            }

            val fragment = when (num) {
                1 -> OrdersFragment().apply { arguments = bundle }
                2 -> HistoryFragment().apply { arguments = bundle }
                3 -> SummaryFragment().apply { arguments = bundle }
                else -> return@observe
            }

            childFragmentManager.beginTransaction().apply {
                replace(binding.mainFragment.id, fragment)
                commit()
            }
        }

        val logoutDialog = getLogoutDialog(viewModel)
        viewModel.logoutPanel.observe(viewLifecycleOwner) {
            if (it) {
                logoutDialog.show()
            } else {
                logoutDialog.dismiss()
            }
        }
        viewModel.logout.observe(viewLifecycleOwner) {
            if (it) {
                logoutDialog.dismiss()
                findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToLoginFragment())
            }
        }

        binding.languageButton.setOnClickListener {
            languagePopup(viewModel)
        }
        viewModel.savedLanguage.observe(viewLifecycleOwner) {
            if (it) {
                when (viewModel.language) {
                    1 -> {
                        changeAppLanguage("ar")
                        sharedPrefs.edit().putString("language_code", "ar").apply()
                        requireActivity().recreate()
                    }

                    2 -> {
                        changeAppLanguage("en")
                        sharedPrefs.edit().putString("language_code", "en").apply()
                        requireActivity().recreate()
                    }

                    3 -> {
                        changeAppLanguage("fr")
                        sharedPrefs.edit().putString("language_code", "fr").apply()
                        requireActivity().recreate()
                    }

                }
                viewModel.unsavedLanguage()
            }
        }

        binding.lifecycleOwner = this
        return binding.root
    }


    private fun getLogoutDialog(viewModel: HomeViewModel): Dialog {
        val dialog = Dialog(requireContext())

        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)

        val binding: LogoutPanelBinding = DataBindingUtil.inflate(
            LayoutInflater.from(requireContext()),
            R.layout.logout_panel,
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


    private fun languagePopup(viewModel: HomeViewModel) {
        val dialog = Dialog(requireContext())

        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)

        val binding: LanguageSelectionPanelBinding = DataBindingUtil.inflate(
            LayoutInflater.from(requireContext()),
            R.layout.language_selection_panel,
            null,
            false
        )

        dialog.setContentView(binding.root)

        dialog.window?.setLayout(
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )

        dialog.setCancelable(true)

        binding.viewmodel = viewModel

        dialog.show()

    }

    private fun changeAppLanguage(languageCode: String) {
        val locale = Locale(languageCode)
        Locale.setDefault(locale)

        val resources = requireContext().resources
        val config = resources.configuration
        config.setLocale(locale)

        resources.updateConfiguration(config, resources.displayMetrics)
    }
}