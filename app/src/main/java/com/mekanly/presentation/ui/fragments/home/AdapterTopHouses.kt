package com.mekanly.presentation.ui.fragments.home

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mekanly.R
import com.mekanly.data.dataModels.DataHouse
import com.mekanly.databinding.ItemAdvSmallBinding

class AdapterTopHouses(private val properties: List<DataHouse>) :
    RecyclerView.Adapter<AdapterTopHouses.PropertyViewHolder>() {
    inner class PropertyViewHolder(private val binding: ItemAdvSmallBinding) :
        RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bind(property: DataHouse) {
            binding.apply {
                tvMainTitle.text = property.name
                tvPrice.text = "${property.price} TMT"
                tvAddressTime.text =
                    "Location: ${property.location.name}, ${property.location.parent_name}"
                tvDescription.text = property.description
                if (property.images.isNotEmpty()) {
                    Glide.with(itemView.context)
                        .load(property.images[0].url)
                        .placeholder(R.drawable.placeholder)
                        .into(propertyImage)
                }
            }
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PropertyViewHolder {
        val binding = ItemAdvSmallBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return PropertyViewHolder(binding)
    }
    override fun onBindViewHolder(holder: PropertyViewHolder, position: Int) {
        val property = properties[position]
        holder.bind(property)
    }
    override fun getItemCount() = properties.size
}