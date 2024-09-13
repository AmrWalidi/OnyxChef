package com.example.android.chefapp.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.android.chefapp.databinding.ItemNameCountListItemBinding
import com.example.android.chefapp.domain.SummaryItems

class SummaryItemAdapter : ListAdapter<SummaryItems, SummaryItemAdapter.ItemViewHolder>(SummaryItemDiffCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return ItemViewHolder.from(parent)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }


    class ItemViewHolder private constructor(private val binding: ItemNameCountListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(
            item: SummaryItems
        ) {
            binding.summaryItem = item
        }

        companion object {
            fun from(parent: ViewGroup): ItemViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val view = ItemNameCountListItemBinding.inflate(layoutInflater, parent, false)
                return ItemViewHolder(view)
            }
        }
    }
}

class SummaryItemDiffCallback : DiffUtil.ItemCallback<SummaryItems>() {
    override fun areItemsTheSame(oldItem: SummaryItems, newItem: SummaryItems): Boolean {
        return oldItem.name == newItem.name
    }

    override fun areContentsTheSame(oldItem: SummaryItems, newItem: SummaryItems): Boolean {
        return oldItem == newItem
    }

}