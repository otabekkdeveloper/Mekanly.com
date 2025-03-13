package com.mekanly.presentation.ui.adapters.pagerAdapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mekanly.R
import com.mekanly.databinding.ItemImageSliderNotificationsBinding

class ImageSliderNotifications(
    private val images: List<String>
) : RecyclerView.Adapter<ImageSliderNotifications.ImageViewHolder>() {  // Убедитесь, что расширяете RecyclerView.Adapter

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val binding = ItemImageSliderNotificationsBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ImageViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        holder.bind(images[position])
    }

    override fun getItemCount(): Int = images.size

    inner class ImageViewHolder(
        private val binding: ItemImageSliderNotificationsBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(imageUrl: String) {
            Glide.with(binding.root)
                .load(imageUrl)
                .placeholder(R.drawable.placeholder)
                .centerCrop()
                .into(binding.propertyImage)
        }
    }
}
