package com.mekanly.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mekanly.data.models.Option
import com.mekanly.databinding.ItemOpportunityDialogBinding

class PossibilitySelectedAdapter : RecyclerView.Adapter<PossibilitySelectedAdapter.OptionViewHolder>() {

    private val selectedList = mutableListOf<Option>()

    fun setOptions(options: List<Option>) {
        selectedList.clear()
        selectedList.addAll(options)
        notifyDataSetChanged()
    }

    inner class OptionViewHolder(val binding: ItemOpportunityDialogBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(option: Option) {
            binding.textView.text = option.name

            // Загрузка иконки из строки (если это URL, нужно использовать Glide или Coil)
            option.icon?.let {
                Glide.with(binding.imageView.context)
                    .load(it)
                    .into(binding.imageView)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OptionViewHolder {
        val binding = ItemOpportunityDialogBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return OptionViewHolder(binding)
    }

    override fun onBindViewHolder(holder: OptionViewHolder, position: Int) {
        holder.bind(selectedList[position])
    }

    override fun getItemCount(): Int = selectedList.size
}
