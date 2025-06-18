package com.mekanly.presentation.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mekanly.data.models.BusinessProfile
import com.mekanly.databinding.ItemBusinessProfileBinding

class AdapterItemBusinessProfile(
    private val items: List<BusinessProfile>
) : RecyclerView.Adapter<AdapterItemBusinessProfile.ViewHolder>() {

    inner class ViewHolder(val binding: ItemBusinessProfileBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemBusinessProfileBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        with(holder.binding) {
            tvPropertyType.text = item.brand
//            tvDescription.text = item.description
            Glide.with(imgProperty.context).load(item.image).into(imgProperty)
        }
    }

    override fun getItemCount() = items.size
}
