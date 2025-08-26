package com.mekanly.presentation.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mekanly.data.DataTopNotifications
import com.mekanly.databinding.ItemTopNotificationsBinding

class AdapterTopNotifications(
    private val items: List<DataTopNotifications>
) : RecyclerView.Adapter<AdapterTopNotifications.ViewHolder>() {

    inner class ViewHolder(val binding: ItemTopNotificationsBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemTopNotificationsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        with(holder.binding) {

            tvPropertyType.text = item.title
            tvDescription.text = item.description
            location.text = item.location
            dataNotifications.text = item.date
            Glide.with(imgProperty.context).load(item.image).into(imgProperty)
            price.text = item.price
        }
    }

    override fun getItemCount() = items.size
}
