package com.mekanly.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mekanly.data.models.CategoryProduct
import com.mekanly.data.models.ProductCategory
import com.mekanly.databinding.ItemSubCategoryBinding

class AdapterSubCategories() :
    RecyclerView.Adapter<RecyclerView.ViewHolder>(

    ) {
    var products: List<ProductCategory> = emptyList()
        set(value) {
            field = value
            notifyItemRangeInserted(0, products.size)
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding = ItemSubCategoryBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return HolderSubCategories(binding)
    }

    override fun getItemCount(): Int {
        return products.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
    }

    inner class HolderSubCategories(private val binding: ItemSubCategoryBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(product:ProductCategory) {

            binding.tvMainTitle.text = product.name?:""
            binding.tvDescription.text = product.description
            binding.tvPrice.text=product.

        }

    }


}