package com.example.android.chefapp.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.android.chefapp.databinding.OrderItemBinding
import com.example.android.chefapp.domain.OrderItem

class ItemAdapter : ListAdapter<OrderItem, ItemAdapter.ItemViewHolder>(ItemDiffCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return ItemViewHolder.from(parent)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }


    class ItemViewHolder private constructor(private val binding: OrderItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(
            item: OrderItem
        ) {
            binding.item = item
        }

        companion object {
            fun from(parent: ViewGroup): ItemViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val view = OrderItemBinding.inflate(layoutInflater, parent, false)
                return ItemViewHolder(view)
            }
        }
    }
}

class ItemDiffCallback : DiffUtil.ItemCallback<OrderItem>() {
    override fun areItemsTheSame(oldItem: OrderItem, newItem: OrderItem): Boolean {
        return oldItem.name == newItem.name
    }

    override fun areContentsTheSame(oldItem: OrderItem, newItem: OrderItem): Boolean {
        return oldItem == newItem
    }

}