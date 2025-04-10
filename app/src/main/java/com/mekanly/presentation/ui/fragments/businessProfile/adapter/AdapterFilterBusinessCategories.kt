package com.mekanly.presentation.ui.fragments.businessProfile.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mekanly.R
import com.mekanly.data.dataModels.DataBusinessProfileCategory
import com.mekanly.databinding.ItemCategoriesBusinessBinding
import com.mekanly.databinding.ItemLocationBottomSheetBinding


class AdapterFilterBusinessCategories(
    private val items: List<DataBusinessProfileCategory>,
    private val onItemClick: ((DataBusinessProfileCategory) -> Unit)? = null
) : RecyclerView.Adapter<AdapterFilterBusinessCategories.BusinessProfileViewHolder>() {

    inner class BusinessProfileViewHolder(
        private val binding: ItemLocationBottomSheetBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: DataBusinessProfileCategory) {
            binding.apply {
                textView.text = item.title



                root.setOnClickListener {
                    onItemClick?.invoke(item)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BusinessProfileViewHolder {
        val binding = ItemLocationBottomSheetBinding.inflate(
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