package com.mekanly.presentation.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mekanly.R
import com.mekanly.data.models.BusinessCategory
import com.mekanly.databinding.ItemCategoriesBusinessBinding


class AdapterItemBusinessCategories(
    private val items: List<BusinessCategory>,
    private val onItemClick: ((BusinessCategory) -> Unit)? = null
) : RecyclerView.Adapter<AdapterItemBusinessCategories.BusinessProfileViewHolder>() {

    inner class BusinessProfileViewHolder(
        private val binding: ItemCategoriesBusinessBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: BusinessCategory) {
            binding.apply {
                tvTitle.text = item.title

               count.text = item.count.toString()

                item.imageResId?.let {
                    ivCategory.setImageResource(it)
                }
                Glide.with(ivCategory.context).load(item.image).placeholder(R.drawable.bg_section_business_profile).into(ivCategory)

                root.setOnClickListener {
                    onItemClick?.invoke(item)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BusinessProfileViewHolder {
        val binding = ItemCategoriesBusinessBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return BusinessProfileViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BusinessProfileViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size
}