package com.mekanly.ui.adapters

import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mekanly.databinding.ItemImageBinding
import com.mekanly.databinding.ItemLocalImageBinding

class AdapterLocalImages(
    private val onRemove: (Uri) -> Unit
) : RecyclerView.Adapter<AdapterLocalImages.ImageViewHolder>() {

    private val images = mutableListOf<Uri>()

    fun submitList(newList: List<Uri>) {
        images.clear()
        images.addAll(newList)
        notifyDataSetChanged()
    }

    inner class ImageViewHolder(val binding: ItemLocalImageBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(uri: Uri) {
            Glide.with(binding.imageView.context)
                .load(uri)
                .centerCrop()
                .into(binding.imageView)

            binding.btnDelete.setOnClickListener {
                onRemove(uri)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val binding = ItemLocalImageBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ImageViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        holder.bind(images[position])
    }

    override fun getItemCount(): Int = images.size
}