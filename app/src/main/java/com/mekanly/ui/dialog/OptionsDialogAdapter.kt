package com.mekanly.ui.dialog

import android.view.LayoutInflater
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mekanly.R
import com.mekanly.data.models.Option
import com.mekanly.databinding.ItemDialogPropertiesBinding
import com.mekanly.databinding.ItemOpportunityDialogBinding
import com.mekanly.presentation.ui.enums.Possibilities
import com.mekanly.ui.enums.Properties


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
                if (isSelected) R.drawable.bg_selected_btn_dialog_options
                else R.drawable.bg_btn_unselected_dialog_options
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

            val propertiesEnum = Properties.entries.find {
                it.key.equals(item.name, ignoreCase = true) ||
                        it.name.equals(item.name, ignoreCase = true)
            }

            val context = binding.textView.context
            binding.textView.text = when (propertiesEnum) {
                Properties.APARTMENT -> context.getString(R.string.apartment)
                Properties.ELITE -> context.getString(R.string.elite)
                Properties.COTTAGE -> context.getString(R.string.cottage)
                Properties.SEMI_ELITE -> context.getString(R.string.semi_elite)
                Properties.VALLEY -> context.getString(R.string.valley)
                Properties.PLAN_HOUSE -> context.getString(R.string.plan_house)
                else -> item.name
            }

            val iconRes = when (propertiesEnum) {

                Properties.APARTMENT -> R.drawable.ic_apartment
                Properties.ELITE -> R.drawable.ic_elite
                Properties.COTTAGE -> R.drawable.ic_cottage
                Properties.SEMI_ELITE -> R.drawable.ic_semi_elite
                Properties.VALLEY -> R.drawable.ic_valley
                Properties.PLAN_HOUSE -> R.drawable.ic_plan_house
                else -> R.drawable.house_logo
            }

            binding.imageView.setImageResource(iconRes)





        }
    }

    inner class RepairTypeViewHolder(private val binding: ItemOpportunityDialogBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Option) {
            binding.textView.text = item.name
            binding.imageView.visibility = GONE

            val isSelected = selectedItems.contains(item)

            binding.layBackground.setBackgroundResource(
                if (isSelected) R.drawable.bg_selected_btn_dialog_options
                else R.drawable.bg_btn_unselected_dialog_options
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


        inner class OpportunitiesViewHolder(private val binding: ItemOpportunityDialogBinding) :
            RecyclerView.ViewHolder(binding.root) {

            fun bind(item: Option) {
                val possibilityEnum = Possibilities.entries.find {
                    it.key.equals(item.name, ignoreCase = true) ||
                            it.name.equals(item.name, ignoreCase = true)
                }

                val context = binding.textView.context
                binding.textView.text = when (possibilityEnum) {
                    Possibilities.WIFI -> context.getString(R.string.wifi)
                    Possibilities.WASHER -> context.getString(R.string.washer)
                    Possibilities.TV -> context.getString(R.string.tv)
                    Possibilities.CONDITIONER -> context.getString(R.string.conditioner)
                    Possibilities.WARDROBE -> context.getString(R.string.wardrobe)
                    Possibilities.BED -> context.getString(R.string.bed)
                    Possibilities.HOT -> context.getString(R.string.hot)
                    Possibilities.FRIDGE -> context.getString(R.string.fridge)
                    Possibilities.SHOWER -> context.getString(R.string.shower)
                    Possibilities.KITCHEN -> context.getString(R.string.kitchen)
                    Possibilities.HOT_WATER -> context.getString(R.string.hot_water)
                    else -> item.name
                }

                val iconRes = when (possibilityEnum) {
                    Possibilities.WIFI -> R.drawable.ic_wifi
                    Possibilities.POOL -> R.drawable.ic_swimming_pool
                    Possibilities.BALCONY -> R.drawable.ic_balcony
                    Possibilities.ELEVATOR -> R.drawable.ic_lift
                    Possibilities.KITCHEN_FURNITURE -> R.drawable.ic_kitchen_furniture
                    Possibilities.WASHER -> R.drawable.ic_washing_machine
                    Possibilities.TV -> R.drawable.ic_tv
                    Possibilities.HOT -> R.drawable.ic_heating_system
                    Possibilities.STOVE -> R.drawable.ic_stove
                    Possibilities.WORK_DESK -> R.drawable.ic_table
                    Possibilities.CONDITIONER -> R.drawable.ic_air_conditioner
                    Possibilities.WARDROBE -> R.drawable.ic_wardrobe
                    Possibilities.BED -> R.drawable.ic_bedroom
                    Possibilities.MANGAL -> R.drawable.ic_bbq
                    Possibilities.HOT_WATER -> R.drawable.ic_hot_water
                    Possibilities.FRIDGE -> R.drawable.ic_fridge
                    Possibilities.SHOWER -> R.drawable.ic_bath
                    Possibilities.KITCHEN -> R.drawable.ic_kitchen
                    else -> R.drawable.ic_wifi
                }

                binding.imageView.setImageResource(iconRes)

                val isSelected = selectedItems.contains(item)
                binding.layBackground.setBackgroundResource(
                    if (isSelected) R.drawable.bg_selected_btn_dialog_options
                    else R.drawable.bg_btn_unselected_dialog_options
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
