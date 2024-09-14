package com.example.android.chefapp.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.android.chefapp.databinding.OrderNumberStatusListItemBinding
import com.example.android.chefapp.domain.Order
import com.example.android.chefapp.viewmodel.HistoryViewModel

class HistoryOrderAdapter(val viewModel: HistoryViewModel) :
    ListAdapter<Order, HistoryOrderAdapter.HistoryOrderViewHolder>(HistoryOrderDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryOrderViewHolder {
        return HistoryOrderViewHolder.from(parent)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: HistoryOrderViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item, viewModel)
    }

    class HistoryOrderViewHolder private constructor(val binding: OrderNumberStatusListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(
            item: Order,
            viewModel: HistoryViewModel
        ) {
            binding.order = item
            binding.viewModel = viewModel
        }

        companion object {
            fun from(parent: ViewGroup): HistoryOrderViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val view = OrderNumberStatusListItemBinding.inflate(layoutInflater, parent, false)
                return HistoryOrderViewHolder(view)
            }
        }
    }
}

class HistoryOrderDiffCallback : DiffUtil.ItemCallback<Order>() {
    override fun areItemsTheSame(oldItem: Order, newItem: Order): Boolean {
        return oldItem.no == newItem.no
    }

    override fun areContentsTheSame(oldItem: Order, newItem: Order): Boolean {
        return oldItem == newItem
    }

}