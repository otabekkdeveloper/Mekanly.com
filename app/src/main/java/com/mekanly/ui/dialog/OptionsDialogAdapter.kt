package com.mekanly.ui.dialog

import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.mekanly.R
import com.mekanly.data.models.Option
import com.mekanly.databinding.ItemDialogPropertiesBinding
import com.mekanly.databinding.ItemOpportunityDialogBinding
import com.mekanly.databinding.ItemOpportunityInSingleHouseBinding



class OptionsDialogAdapter(
    private val type: Int,
    private val items: List<Option>,
    private val singleSelection: Boolean = false
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        const val TYPE_PROPERTIES = 0
        const val TYPE_REPAIR = 1
        const val TYPE_OPPORTUNITY = 2
    }

    private val selectedItems = mutableListOf<Option>()


    override fun getItemViewType(position: Int): Int {
        return type
    }

    inner class PropertiesViewHolder(private val binding: ItemDialogPropertiesBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Option) {
            binding.textView.text = item.name
            binding.imageView.visibility = VISIBLE
            val isSelected = selectedItems.contains(item)

            binding.layButton.setBackgroundResource(
                if (isSelected) R.drawable.bg_selected_properties_btn
                else R.drawable.emlakler_btn_bg
            )

            binding.layButton.setOnClickListener {
                if (singleSelection) {
                    selectedItems.clear()
                    selectedItems.add(item)
                    notifyDataSetChanged()
                } else {
                    if (selectedItems.contains(item)) {
                        selectedItems.remove(item)
                    } else {
                        selectedItems.add(item)
                    }
                    notifyItemChanged(bindingAdapterPosition)
                }
            }
        }
    }

    inner class RepairTypeViewHolder(private val binding: ItemOpportunityDialogBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Option) {
            binding.textView.text = item.name
            binding.imageView.visibility = GONE

            val isSelected = selectedItems.contains(item)

            binding.layBackground.setBackgroundResource(
                if (isSelected) R.drawable.bg_selected_properties_btn
                else R.drawable.emlakler_btn_bg
            )

            binding.layBackground.setOnClickListener {
                if (singleSelection) {
                    selectedItems.clear()
                    selectedItems.add(item)
                    notifyDataSetChanged()
                } else {
                    if (selectedItems.contains(item)) {
                        selectedItems.remove(item)
                    } else {
                        selectedItems.add(item)
                    }
                    notifyItemChanged(bindingAdapterPosition)
                }
            }
        }
    }

    inner class OpportunitiesViewHolder(private val binding: ItemOpportunityDialogBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Option) {
            binding.textView.text = item.name
            binding.imageView.visibility = VISIBLE
            val isSelected = selectedItems.contains(item)

            binding.layBackground.setBackgroundResource(
                if (isSelected) R.drawable.bg_selected_properties_btn
                else R.drawable.emlakler_btn_bg
            )

            binding.layBackground.setOnClickListener {
                if (singleSelection) {
                    selectedItems.clear()
                    selectedItems.add(item)
                    notifyDataSetChanged()
                } else {
                    if (selectedItems.contains(item)) {
                        selectedItems.remove(item)
                    } else {
                        selectedItems.add(item)
                    }
                    notifyItemChanged(bindingAdapterPosition)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            TYPE_PROPERTIES -> {
                val binding = ItemDialogPropertiesBinding.inflate(layoutInflater, parent, false)
                PropertiesViewHolder(binding)
            }
            TYPE_OPPORTUNITY -> {
                val binding = ItemOpportunityDialogBinding.inflate(layoutInflater, parent, false)
                OpportunitiesViewHolder(binding)
            }
            else -> {
                val binding = ItemOpportunityDialogBinding.inflate(layoutInflater, parent, false)
                RepairTypeViewHolder(binding)
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = items[position]
        when (holder) {
            is OpportunitiesViewHolder -> holder.bind(item)
            is RepairTypeViewHolder -> holder.bind(item)
            is PropertiesViewHolder -> holder.bind(item)
        }
    }


    override fun getItemCount(): Int = items.size

    fun getSelectedItems(): List<Option> = selectedItems.toList()

    fun setSelectedItems(newSelectedItems: List<Option>) {
        selectedItems.clear()
        selectedItems.addAll(newSelectedItems)
        notifyDataSetChanged()
    }

    fun clearSelection() {
        selectedItems.clear()
        notifyDataSetChanged()
    }

    fun setAllSelected(selected: Boolean) {
        selectedItems.clear()
        if (selected) {
            selectedItems.addAll(items)
        }
        notifyDataSetChanged()
    }
}
