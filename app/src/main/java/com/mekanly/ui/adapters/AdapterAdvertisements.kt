package com.mekanly.presentation.ui.adapters

import android.annotation.SuppressLint
import android.view.GestureDetector
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.NavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mekanly.R
import com.mekanly.data.models.House
import com.mekanly.databinding.ItemAdvBigBinding
import com.mekanly.presentation.ui.enums.Categories
import com.mekanly.presentation.ui.fragments.flow.FragmentFlowDirections
import com.mekanly.presentation.ui.fragments.search.ImageSliderAdapter
import com.mekanly.ui.fragments.favorite.FavoritesViewModel
import com.mekanly.ui.fragments.search.viewModel.VMSearch

class AdapterAdvertisements(
    private var properties: List<House>,
    private val viewModel: VMSearch,
    private val viewModelFavorite: FavoritesViewModel,
    private val navController: NavController
) : RecyclerView.Adapter<AdapterAdvertisements.PropertyViewHolder>() {

    inner class PropertyViewHolder(private val binding: ItemAdvBigBinding) :
        RecyclerView.ViewHolder(binding.root) {

        @SuppressLint("SetTextI18n", "ClickableViewAccessibility")
        fun bind(property: House) {
            binding.item = property
            binding.apply {
                tvMainTitle.text = property.name
                tvPrice.text = "${property.price} TMT"
                tvAddressTime.text = "${property.location?.parentName}, ${property.location?.name}"
                tvDescription.text = property.description
                countComment.text = "(${property.commentCount})"

                if (property.exclusive == 1) {
                    onlyMekanlyCom.visibility = View.VISIBLE
                    viewForOnlyMekanlyCom.visibility = View.VISIBLE
                }

                // ✅ ИСПРАВЛЕНО: Используем поле favorite из модели
                updateLikeButton(property.favorite)

                btnLike.setOnClickListener {
                    val currentLikeStatus = property.favorite


                    property.favorite = !currentLikeStatus
                    updateLikeButton(property.favorite)

                    // Обновляем через ViewModel
                    viewModelFavorite.toggleLike(property.id, currentLikeStatus, "House")

                    val message = if (currentLikeStatus) {
                        "Halanlarymdan aýryldy"
                    } else {
                        "Halanlaryma goşuldy"
                    }
                    Toast.makeText(root.context, message, Toast.LENGTH_SHORT).show()
                }

                if (property.vipStatus) {
                    bgAdv.setBackgroundResource(R.drawable.bg_vip_houses)
                    vipLogo.visibility = View.VISIBLE
                }

                if (property.luxeStatus) {
                    advType.visibility = View.GONE
                    luxLogo.visibility = View.VISIBLE
                    bgAdv.setBackgroundResource(R.drawable.bg_lux_houses)
                }

                val categoriesEnum = Categories.entries.find {
                    it.key.equals(property.categoryName, ignoreCase = true) ||
                            it.name.equals(property.categoryName, ignoreCase = true)
                }

                advType.text = when (categoriesEnum) {
                    Categories.SATLYK_JAY -> root.context.getString(R.string.house_for_sale)
                    Categories.KIREYNE_JAYLAR -> root.context.getString(R.string.house_for_rent)
                    Categories.KIREYNE_OTAGLAR -> root.context.getString(R.string.room_for_rent)
                    Categories.KIREYNE_OFISLER -> root.context.getString(R.string.office_for_rent)
                    Categories.KIREYNE_SOWDA_EMLAKLER -> root.context.getString(R.string.commercial_rent)
                    Categories.SATLYK_SOWDA_EMLAKLER -> root.context.getString(R.string.commercial_sale)
                    Categories.BEYLEKI_EMLAKLER -> root.context.getString(R.string.other_real_estate)
                    else -> property.categoryName
                }

                if (property.images.isNotEmpty()) {
                    val imageUrls = property.images.map { it.url }
                    val adapter = ImageSliderAdapter(imageUrls)
                    viewPagerImages.adapter = adapter
                    wormDotsIndicator.attachTo(viewPagerImages)
                }

                root.setOnClickListener {
                    val action = FragmentFlowDirections
                        .actionHomeFragmentToFragmentSingleHouse(property.id.toLong())
                    navController.navigate(action)
                }

                val gestureDetector = GestureDetector(itemView.context, object : GestureDetector.SimpleOnGestureListener() {
                    override fun onSingleTapConfirmed(e: MotionEvent): Boolean {
                        val action = FragmentFlowDirections
                            .actionHomeFragmentToFragmentSingleHouse(property.id.toLong())
                        navController.navigate(action)
                        return true
                    }
                })

                viewPagerImages.getChildAt(0).setOnTouchListener { _, event ->
                    gestureDetector.onTouchEvent(event)
                    false
                }

                val propertiesList = property.possibilities ?: emptyList()
                val iconsAdapter = AdapterIconsAdvBig(propertiesList)
                crProperties.adapter = iconsAdapter
                crProperties.layoutManager = LinearLayoutManager(
                    binding.root.context,
                    LinearLayoutManager.HORIZONTAL,
                    false
                )
            }
        }

        private fun updateLikeButton(isLiked: Boolean) {
            binding.btnLike.setImageResource(
                if (isLiked) R.drawable.ic_favorite_selected else R.drawable.favourite_three
            )
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PropertyViewHolder {
        val binding = ItemAdvBigBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return PropertyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PropertyViewHolder, position: Int) {
        holder.bind(properties[position])
    }

    override fun getItemCount() = properties.size

    fun updateList() {
        val oldSize = properties.size
        val newList = viewModel.houses.value
        val newSize = newList.size

        if (viewModel.needToReinitialise()) {
            properties = newList
            notifyDataSetChanged()
            viewModel.setReinitialiseFalse()
        } else if (newSize > oldSize) {
            properties = newList
            notifyItemRangeInserted(oldSize, newSize - oldSize)
        } else if (newSize != oldSize) {
            properties = newList
            notifyDataSetChanged()
        }
    }
}