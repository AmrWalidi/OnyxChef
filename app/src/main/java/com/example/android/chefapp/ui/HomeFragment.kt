package com.example.android.chefapp.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.android.chefapp.R
import com.example.android.chefapp.databinding.FragmentHomeBinding
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

        viewModel.fragmentNumber.observe(viewLifecycleOwner) { num ->
            val bundle = Bundle().apply {
                viewModel.user.value?.let{
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
        return binding.root
    }
}