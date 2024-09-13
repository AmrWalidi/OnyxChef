package com.example.android.chefapp.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.android.chefapp.R
import com.example.android.chefapp.adapters.SummaryItemAdapter
import com.example.android.chefapp.databinding.FragmentSummaryBinding
import com.example.android.chefapp.viewmodel.SummaryViewModel

class SummaryFragment : Fragment() {

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

        val viewModel: SummaryViewModel by lazy {
            val activity = requireNotNull(this.activity)
            ViewModelProvider(
                this,
                SummaryViewModel.Factory(activity.application, terminal, branch)
            )[SummaryViewModel::class.java]
        }

        val binding: FragmentSummaryBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_summary, container, false)

        binding.viewModel = viewModel

        val firstAdapter = SummaryItemAdapter()
        val secondAdapter = SummaryItemAdapter()

        binding.itemCountList1.adapter = firstAdapter
        binding.itemCountList2.adapter = secondAdapter

        viewModel.items.observe(viewLifecycleOwner) {
            it?.let {
                firstAdapter.submitList(it.subList(0, it.size.div(2)))
                secondAdapter.submitList(it.subList(it.size.div(2), it.size))
                if (it.isEmpty()) {
                    binding.noItemContainer.visibility = View.VISIBLE
                    binding.summaryBody.visibility = View.GONE
                } else {
                    binding.noItemContainer.visibility = View.GONE
                    binding.summaryBody.visibility = View.VISIBLE
                }
            }
        }

        viewModel.orderStates.observe(viewLifecycleOwner) {
            if(it.isNotEmpty()){
                binding.all0rderNumber.text = it[0].count.toString()
                binding.cancelOrderNumber.text = it[1].count.toString()
                binding.delayedOrderNumber.text = it[2].count.toString()

            }
        }

        viewModel.orderType.observe(viewLifecycleOwner) {
            if(it.isNotEmpty()) {
                binding.dineInNumber.text = it[0].count.toString()
                binding.takeAwayNumber.text = it[1].count.toString()
                binding.delivaryOrderNumber.text = it[2].count.toString()

            }
        }

        binding.lifecycleOwner = this


        return binding.root
    }
}