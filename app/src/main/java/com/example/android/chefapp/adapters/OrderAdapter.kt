package com.example.android.chefapp.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.android.chefapp.databinding.OrderContainerBinding
import com.example.android.chefapp.domain.Order

class OrderAdapter : ListAdapter<Order, OrderAdapter.OrderViewHolder>(OrderDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderViewHolder {
        return OrderViewHolder.from(parent)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: OrderViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }


    class OrderViewHolder private constructor(val binding: OrderContainerBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(
            item: Order
        ) {
            binding.order = item
            val itemAdapter = ItemAdapter()
            binding.orderItemList.adapter = itemAdapter
            itemAdapter.submitList(item.items)
        }

        companion object {
            fun from(parent: ViewGroup): OrderViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val view = OrderContainerBinding.inflate(layoutInflater, parent, false)
                return OrderViewHolder(view)
            }
        }
    }
}

class OrderDiffCallback : DiffUtil.ItemCallback<Order>() {
    override fun areItemsTheSame(oldItem: Order, newItem: Order): Boolean {
        return oldItem.no == newItem.no
    }

    override fun areContentsTheSame(oldItem: Order, newItem: Order): Boolean {
        return oldItem == newItem
    }

}