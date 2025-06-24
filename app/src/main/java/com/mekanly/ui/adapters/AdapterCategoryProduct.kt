package com.mekanly.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mekanly.R
import com.mekanly.data.models.CategoryProduct
import com.mekanly.databinding.ItemSubCategoryBinding
import com.mekanly.presentation.ui.fragments.search.ImageSliderAdapter

class AdapterCategoryProduct() :
    RecyclerView.Adapter<RecyclerView.ViewHolder>(

    ) {
    var products: List<CategoryProduct> = emptyList()
        set(value) {
            field = value
            notifyItemRangeInserted(0, products.size)
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding = ItemSubCategoryBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return HolderCategoryProduct(binding)
    }

    override fun getItemCount(): Int {
        return products.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as HolderCategoryProduct).bind(products[position])
    }

    inner class HolderCategoryProduct(private val binding: ItemSubCategoryBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(product: CategoryProduct) {
            with(binding) {
                tvMainTitle.text = product.name
                tvDescription.text = product.description
                tvPrice.text = "${product.price}  TMT"

                updateLikeButton(product.favorite)

                if (product.images.isNotEmpty()) {
                    val imageUrls = product.images.map { it.path }
                    val adapter = ImageSliderAdapter(imageUrls)
                    viewPagerImages.adapter = adapter
                    wormDotsIndicator.attachTo(viewPagerImages)
                }

                if (product.vip) {
                    bgAdv.setBackgroundResource(R.drawable.bg_vip_houses)
                    vipLogo.visibility = View.VISIBLE
                } else {
                    bgAdv.setBackgroundResource(R.color.white)
                    vipLogo.visibility = View.GONE
                }


                btnLike.setOnClickListener {
                    val currentLikeStatus = product.favorite
                    product.favorite = !currentLikeStatus

                    updateLikeButton(!currentLikeStatus)
                }


            }
        }

        private fun updateLikeButton(isLiked: Boolean) {
            binding.btnLike.setImageResource(
                if (isLiked) R.drawable.ic_favorite_selected else R.drawable.favourite_three
            )
        }


    }

}


