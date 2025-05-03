package com.mekanly.presentation.ui.fragments.home

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mekanly.R
import com.mekanly.data.models.House
import com.mekanly.databinding.ItemAdvSmallBinding
import com.mekanly.presentation.ui.fragments.flow.FragmentFlowDirections

class AdapterTopHouses(private val navController: NavController) :
    RecyclerView.Adapter<AdapterTopHouses.PropertyViewHolder>() {

    private val properties = mutableListOf<House>()

    inner class PropertyViewHolder(private val binding: ItemAdvSmallBinding) :
        RecyclerView.ViewHolder(binding.root) {

        @SuppressLint("SetTextI18n")
        fun bind(property: House) {
            binding.item = property
            binding.apply {
                tvMainTitle.text = property.name
                tvPrice.text = "${property.price} TMT"
                tvAddressTime.text =
                    "${property.location?.parentName}, ${property.location?.name}"
                tvDescription.text = property.description
                if (property.images != null && property.images.isNotEmpty()) {
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
        binding.adapter = this@AdapterTopHouses
        return PropertyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PropertyViewHolder, position: Int) {
        val property = properties[position]
        holder.bind(property)
    }

    override fun getItemCount() = properties.size

    fun onAdvClicked(item: House) {
        val action =
            FragmentFlowDirections.actionHomeFragmentToFragmentSingleHouse(item.id.toLong())
        navController.navigate(action)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setItems(newItems: List<House>) {
        properties.clear()
        properties.addAll(newItems)
        notifyDataSetChanged()
    }

    @SuppressLint("NotifyItemRangeInserted")
    fun addItems(moreItems: List<House>) {
        val startPosition = properties.size
        properties.addAll(moreItems)
        notifyItemRangeInserted(startPosition, moreItems.size)
    }
}
