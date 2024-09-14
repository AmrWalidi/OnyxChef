package com.example.android.chefapp.ui

import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.android.chefapp.R
import com.example.android.chefapp.databinding.FragmentHomeBinding
import com.example.android.chefapp.databinding.LogoutPanelBinding
import com.example.android.chefapp.viewmodel.HomeViewModel

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

        val dialog = showPopup(viewModel)

        viewModel.logoutPanel.observe(viewLifecycleOwner) {
            if (it) {
                dialog.show()
            } else {
                dialog.dismiss()
            }
        }
        viewModel.logout.observe(viewLifecycleOwner) {
            if (it) {
                dialog.dismiss()
                findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToLoginFragment())
            }
        }
        return binding.root
    }

    private fun showPopup(viewModel: HomeViewModel): Dialog {
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

}