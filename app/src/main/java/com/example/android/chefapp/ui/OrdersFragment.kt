package com.example.android.chefapp.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.android.chefapp.R
import com.example.android.chefapp.databinding.FragmentOrdersBinding
import com.example.android.chefapp.viewmodel.OrdersViewModel

class OrdersFragment : Fragment() {

    private var terminal: Int? = null
    private var branch: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            terminal = it.getInt("terminal", 0)
            branch = it.getInt("branch", 0)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val viewModel: OrdersViewModel by lazy {
            val activity = requireNotNull(this.activity)
            ViewModelProvider(
                this,
                OrdersViewModel.Factory(activity.application, terminal, branch)
            )[OrdersViewModel::class.java]
        }

        val binding: FragmentOrdersBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_orders, container, false
        )
        binding.viewModel = viewModel

        viewModel.currentPage.observe(viewLifecycleOwner){
            viewModel.getOrders()
        }

        binding.lifecycleOwner = this

        return binding.root
    }
}