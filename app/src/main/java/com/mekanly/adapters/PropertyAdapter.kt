package com.mekanly.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mekanly.R
import com.mekanly.data.responseLocation.House

class PropertyAdapter(private val properties: List<House>) :
    RecyclerView.Adapter<PropertyAdapter.PropertyViewHolder>() {

    inner class PropertyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val propertyName: TextView = view.findViewById(R.id.propertyName)
        val propertyPrice: TextView = view.findViewById(R.id.propertyPrice)
        val propertyLocation: TextView = view.findViewById(R.id.propertyLocation)
        val propertyImage: ImageView = view.findViewById(R.id.propertyImage)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PropertyViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_property, parent, false)
        return PropertyViewHolder(view)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: PropertyViewHolder, position: Int) {
        val property = properties[position]
        holder.propertyName.text = property.name
        holder.propertyPrice.text = "Price: ${property.price} TMT"
        holder.propertyLocation.text = "Location: ${property.location.name}, ${property.location.parent_name}"

        // Загрузка изображения
        if (property.images.isNotEmpty()) {
            Glide.with(holder.itemView.context)
                .load(property.images[0].url)
                .into(holder.propertyImage)
        }
    }

    override fun getItemCount() = properties.size
}
