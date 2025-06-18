package com.mekanly.presentation.ui.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.mekanly.data.SearchingHousesData
import com.mekanly.databinding.ItemSearchingHousesBinding

class SearchingHousesAdapter(private val context: Context,
                             private val adList: List<SearchingHousesData>,
                             private val isTextView1Visible: Boolean,
                             private val isTextView2Visible: Boolean,
    )
    : RecyclerView.Adapter<SearchingHousesAdapter.AdViewHolder>() {

    inner class AdViewHolder(binding: ItemSearchingHousesBinding) : RecyclerView.ViewHolder(binding.root) {
        val tvTitle: TextView = binding.tvTitle
        val tvAddress: TextView = binding.tvAddress
        val tvRooms: TextView = binding.tvRooms
        val tvPropertyType: TextView = binding.tvPropertyType
        val tvDescription: TextView = binding.tvDescription
        val tvStatus: TextView = binding.tvStatus
        val tvPrice: TextView = binding.tvPrice
        val btnViewAd: TextView = binding.btnViewAd
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdViewHolder {
        val binding = ItemSearchingHousesBinding.inflate(LayoutInflater.from(context), parent, false)
        return AdViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AdViewHolder, position: Int) {
        val adItem = adList[position]
        holder.tvTitle.text = adItem.title
        holder.tvAddress.text = adItem.address
        holder.tvRooms.text = adItem.rooms
        holder.tvPropertyType.text = adItem.propertyType
        holder.tvDescription.text = adItem.description
        holder.tvStatus.text = adItem.status
        holder.tvPrice.text = adItem.price


        // Управляем видимостью первого TextView
        holder.tvStatus.visibility = if (isTextView1Visible) View.VISIBLE else View.GONE

        // Управляем видимостью второго TextView
        holder.btnViewAd.visibility = if (isTextView2Visible) View.VISIBLE else View.GONE

    }

    override fun getItemCount(): Int {
        return adList.size
    }
}
