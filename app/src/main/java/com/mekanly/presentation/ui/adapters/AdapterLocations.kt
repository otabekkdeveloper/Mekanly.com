package com.mekanly.presentation.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mekanly.data.dataModels.DataLocation
import com.mekanly.databinding.ItemLocationBottomSheetBinding

class AdapterLocations(
    private val items: List<DataLocation>,
    private val onItemClick: (DataLocation) -> Unit
) : RecyclerView.Adapter<AdapterLocations.ItemViewHolder>() {

    inner class ItemViewHolder(private val binding: ItemLocationBottomSheetBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(location: DataLocation) {
            binding.textView.text = location.name
            binding.root.setOnClickListener { onItemClick(location) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val binding = ItemLocationBottomSheetBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size
}
