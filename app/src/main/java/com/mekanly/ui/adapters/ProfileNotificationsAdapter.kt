package com.mekanly.presentation.ui.adapters


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mekanly.data.ProfileNotificationsData
import com.mekanly.databinding.ItemProfileNotificationsBinding
import com.mekanly.presentation.ui.adapters.pagerAdapters.ImageSliderNotifications

class ProfileNotificationsAdapter(
    private val onItemClick: (ProfileNotificationsData) -> Unit,
    private val onLikeClick: (ProfileNotificationsData) -> Unit
) : ListAdapter<ProfileNotificationsData, ProfileNotificationsAdapter.PropertyViewHolder>(PropertyDiffCallback()) {

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

        fun bind(property: ProfileNotificationsData) {
            binding.apply {
                tvMainTitle.text = property.title
                tvAddressTime.text = property.time
                tvDescription.text = property.address
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
                val imageAdapter = ImageSliderNotifications(property.images)
                viewPagerImages.adapter = imageAdapter
                wormDotsIndicator.setViewPager2(viewPagerImages)

                // Обновление иконки избранного
                btnLike.setIconTintResource(
                    if (property.isFavorite) {
                        com.mekanly.R.color.error
                    } else {
                        com.mekanly.R.color.black
                    }
                )
            }
        }
    }

    private class PropertyDiffCallback : DiffUtil.ItemCallback<ProfileNotificationsData>() {
        override fun areItemsTheSame(oldItem: ProfileNotificationsData, newItem: ProfileNotificationsData): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: ProfileNotificationsData, newItem: ProfileNotificationsData): Boolean {
            return oldItem == newItem
        }
    }
}