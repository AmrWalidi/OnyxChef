package com.example.android.chefapp.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.android.chefapp.R
import com.example.android.chefapp.adapters.HistoryOrderAdapter
import com.example.android.chefapp.adapters.ItemAdapter
import com.example.android.chefapp.databinding.FragmentHistoryBinding
import com.example.android.chefapp.viewmodel.HistoryViewModel

class HistoryFragment : Fragment() {

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
        val viewModel: HistoryViewModel by lazy {
            val activity = requireNotNull(this.activity)
            ViewModelProvider(
                this,
                HistoryViewModel.Factory(activity.application, terminal, branch)
            )[HistoryViewModel::class.java]
        }

        val binding: FragmentHistoryBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_history, container, false)

        binding.viewModel = viewModel

        val orderAdapter = HistoryOrderAdapter(viewModel)
        val itemAdapter = ItemAdapter()

        binding.historyOrderList.adapter = orderAdapter
        binding.orderItemList.adapter = itemAdapter

        val manager = GridLayoutManager(activity, 2)
        binding.orderItemList.layoutManager = manager

        viewModel.orders.observe(viewLifecycleOwner) {
            it?.let {
                orderAdapter.submitList(it)
            }
        }

        viewModel.selectedOrder.observe(viewLifecycleOwner) {
            if (it == null) {
                binding.orderContainer.visibility = View.GONE
            } else {
                itemAdapter.submitList(it.items)
                binding.orderContainer.visibility = View.VISIBLE
            }
        }

        viewModel.selectOrderType.observe(viewLifecycleOwner) {
            when (it) {
                1 -> {
                    binding.allActive.visibility = View.VISIBLE
                    binding.doneActive.visibility = View.INVISIBLE
                    binding.canceledActive.visibility = View.INVISIBLE
                }

                2 -> {
                    binding.allActive.visibility = View.INVISIBLE
                    binding.doneActive.visibility = View.VISIBLE
                    binding.canceledActive.visibility = View.INVISIBLE
                }

                3 -> {
                    binding.allActive.visibility = View.INVISIBLE
                    binding.doneActive.visibility = View.INVISIBLE
                    binding.canceledActive.visibility = View.VISIBLE
                }
            }
        }

        binding.lifecycleOwner = this

        return binding.root
    }
}