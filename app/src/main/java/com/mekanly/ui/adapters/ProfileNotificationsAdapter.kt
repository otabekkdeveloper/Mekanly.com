package com.mekanly.ui.adapters


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mekanly.data.models.House
import com.mekanly.databinding.ItemProfileNotificationsBinding
import com.mekanly.presentation.ui.adapters.pagerAdapters.ImageSliderNotifications
import com.mekanly.utils.extensions.formatIsoDateLegacy

class ProfileNotificationsAdapter(
    private val onItemClick: (House) -> Unit,
    private val onLikeClick: (House) -> Unit
) : ListAdapter<House, ProfileNotificationsAdapter.PropertyViewHolder>(PropertyDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PropertyViewHolder {
        val binding = ItemProfileNotificationsBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return PropertyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PropertyViewHolder, position: Int) {
        val property = getItem(position)
        holder.bind(property)
    }

    inner class PropertyViewHolder(
        private val binding: ItemProfileNotificationsBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                val position = bindingAdapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    onItemClick(getItem(position))
                }
            }

            binding.btnLike.setOnClickListener {
                val position = bindingAdapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    onLikeClick(getItem(position))
                }
            }
        }

        fun bind(property: House) {
            binding.apply {
                tvMainTitle.text = property.name
                tvAddressTime.text = formatIsoDateLegacy(property.createdAt)
                tvDescription.text = property.description
                tvPrice.text = "${property.price} TMT"

                // Установка статуса с соответствующим цветом
                status.text = property.status
                status.setTextColor(
                    if (property.status == "Kabul edildi") {
                        root.context.getColor(com.mekanly.R.color.success)
                    } else {
                        root.context.getColor(com.mekanly.R.color.error)
                    }
                )

                // Настройка ViewPager для изображений
                val imageAdapter = ImageSliderNotifications(property.images.map { it.url })
                viewPagerImages.adapter = imageAdapter
                wormDotsIndicator.setViewPager2(viewPagerImages)

                // Обновление иконки избранного
                btnLike.setIconTintResource(
                    if (property.liked) {
                        com.mekanly.R.color.error
                    } else {
                        com.mekanly.R.color.black
                    }
                )
            }
        }
    }

    private class PropertyDiffCallback : DiffUtil.ItemCallback<House>() {
        override fun areItemsTheSame(oldItem: House, newItem: House): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: House, newItem: House): Boolean {
            return oldItem == newItem
        }
    }
}