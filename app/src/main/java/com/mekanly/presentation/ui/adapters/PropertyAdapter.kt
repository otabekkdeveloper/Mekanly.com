package com.mekanly.presentation.ui.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mekanly.data.dataModels.DataHouse
import com.mekanly.databinding.ItemPropertyBinding

class PropertyAdapter(private val properties: List<DataHouse>) :
    RecyclerView.Adapter<PropertyAdapter.PropertyViewHolder>() {

    inner class PropertyViewHolder(private val binding: ItemPropertyBinding) :
        RecyclerView.ViewHolder(binding.root) {

        @SuppressLint("SetTextI18n")
        fun bind(property: DataHouse) {
            binding.apply {
                propertyName.text = property.name
                propertyPrice.text = "Price: ${property.price} TMT"
                propertyLocation.text = "Location: ${property.location.name}, ${property.location.parent_name}"

                if (property.images.isNotEmpty()) {
                    Glide.with(itemView.context)
                        .load(property.images[0].url)
                        .into(propertyImage)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PropertyViewHolder {
        val binding = ItemPropertyBinding.inflate(
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