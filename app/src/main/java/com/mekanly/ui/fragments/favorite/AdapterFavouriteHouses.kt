package com.mekanly.ui.adapters

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

class AdapterFavouriteHouses(
    private val navController: NavController
) : RecyclerView.Adapter<AdapterFavouriteHouses.FavHouseViewHolder>() {

    private val houseList = mutableListOf<House>()

    inner class FavHouseViewHolder(private val binding: ItemAdvSmallBinding) :
        RecyclerView.ViewHolder(binding.root) {

        @SuppressLint("SetTextI18n")
        fun bind(house: House) {
            binding.tvMainTitle.text = house.name
            binding.tvPrice.text = "${house.price} TMT"
            binding.tvAddressTime.text =
                "${house.location.parentName}, ${house.location.name}"
            binding.tvDescription.text = house.description

            if (house.images.isNotEmpty()) {
                Glide.with(itemView.context)
                    .load(house.images[0].url)
                    .placeholder(R.drawable.placeholder)
                    .into(binding.propertyImage)
            }

            // Переход при клике
            itemView.setOnClickListener {
                navController.navigate(
                    FragmentFlowDirections.actionHomeFragmentToFragmentSingleHouse(house.id.toLong())
                )
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavHouseViewHolder {
        val binding = ItemAdvSmallBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return FavHouseViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FavHouseViewHolder, position: Int) {
        holder.bind(houseList[position])
    }

    override fun getItemCount() = houseList.size

    @SuppressLint("NotifyDataSetChanged")
    fun setItems(newItems: List<House>) {
        houseList.clear()
        houseList.addAll(newItems)
        notifyDataSetChanged()
    }
}
