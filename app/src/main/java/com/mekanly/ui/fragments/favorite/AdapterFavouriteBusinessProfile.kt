package com.mekanly.ui.fragments.favorite

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mekanly.R
import com.mekanly.data.models.ShopProduct
import com.mekanly.databinding.ItemAdvSmallBinding
import com.mekanly.presentation.ui.fragments.flow.FragmentFlowDirections

class AdapterFavouriteBusinessProfile(
    private val navController: NavController
) : RecyclerView.Adapter<AdapterFavouriteBusinessProfile.FavProductsViewHolder>() {

    private val productList = mutableListOf<ShopProduct>()

    inner class FavProductsViewHolder(private val binding: ItemAdvSmallBinding) :
        RecyclerView.ViewHolder(binding.root) {

        @SuppressLint("SetTextI18n")
        fun bind(product: ShopProduct) {
            binding.tvMainTitle.text = product.name
            binding.tvPrice.text = "${product.price} TMT"
            binding.tvAddressTime.text = "${product.locationParent.parentName}, ${product.locationName}"

            binding.tvDescription.text = product.description

            if (product.images.isNotEmpty()) {
                Glide.with(itemView.context)
                    .load(product.images[0].thumbnail)
                    .placeholder(R.drawable.placeholder)
                    .into(binding.propertyImage)
            }

            // Переход при клике
            itemView.setOnClickListener {
                navController.navigate(
                    FragmentFlowDirections.actionHomeFragmentToFragmentSingleHouse(product.id.toLong())
                )
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavProductsViewHolder {
        val binding = ItemAdvSmallBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return FavProductsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FavProductsViewHolder, position: Int) {
        holder.bind(productList[position])
    }

    override fun getItemCount() = productList.size

    @SuppressLint("NotifyDataSetChanged")
    fun setItemsProduct(newItems: List<ShopProduct>) {
        productList.clear()
        productList.addAll(newItems)
        notifyDataSetChanged()
    }
}
