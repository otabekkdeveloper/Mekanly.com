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
import com.mekanly.ui.fragments.search.viewModel.VMSearch

class AdapterAdvertisements(
    private var properties: List<House>,
    private val viewModel: VMSearch,
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
                if (property.exclusive == 1){
                    onlyMekanlyCom.visibility = View.VISIBLE
                    viewForOnlyMekanlyCom.visibility = View.VISIBLE

                }



                btnLike.setOnClickListener{
                    Toast.makeText(root.context, "Like Knopkasy basylandyr jigimjan!", Toast.LENGTH_SHORT).show()
                }



                if (property.vipStatus){
                    bgAdv.setBackgroundResource(R.drawable.bg_vip_houses)
                    vipLogo.visibility = View.VISIBLE
                }

                if (property.luxeStatus){
                    advType.visibility = View.GONE
                    luxLogo.visibility = View.VISIBLE
                    bgAdv.setBackgroundResource(R.drawable.bg_lux_houses)
                }


                val categoriesEnum = Categories.entries.find {
                    it.key.equals(property.categoryName, ignoreCase = true) ||
                            it.name.equals(property.categoryName, ignoreCase = true)
                }
                val context = binding.advType.context

                binding.advType.text = when (categoriesEnum) {
                    Categories.SATLYK_JAY -> context.getString(R.string.house_for_sale)
                    Categories.KIREYNE_JAYLAR -> context.getString(R.string.house_for_rent)
                    Categories.KIREYNE_OTAGLAR -> context.getString(R.string.room_for_rent)
                    Categories.KIREYNE_OFISLER -> context.getString(R.string.office_for_rent)
                    Categories.KIREYNE_SOWDA_EMLAKLER -> context.getString(R.string.commercial_rent)
                    Categories.SATLYK_SOWDA_EMLAKLER -> context.getString(R.string.commercial_sale)
                    Categories.BEYLEKI_EMLAKLER -> context.getString(R.string.other_real_estate)
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
                    val result = gestureDetector.onTouchEvent(event)
                    // Возвращаем false, чтобы не мешать свайпам ViewPager2
                    false
                }
            }

            // В методе bind класса PropertyViewHolder:
            val propertiesList = property.possibilities ?: emptyList()
            val iconsAdapter = AdapterIconsAdvBig(propertiesList)
            binding.crProperties.adapter = iconsAdapter
            binding.crProperties.layoutManager = LinearLayoutManager(binding.root.context, LinearLayoutManager.HORIZONTAL, false
            )


        }


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PropertyViewHolder {
        val binding = ItemAdvBigBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        binding.adapter = this
        return PropertyViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: AdapterAdvertisements.PropertyViewHolder, position: Int
    ) {
        val property = properties[position]
        holder.bind(property)
    }

    override fun getItemCount() = properties.size

    /**
     * Обновляет список данных и корректно уведомляет адаптер о изменениях
     */
    fun updateList() {
        val oldSize = properties.size
        val newList = viewModel.houses.value
        val newSize = newList.size

        if (viewModel.needToReinitialise()) {
            // Полное обновление списка
            properties = newList
            notifyDataSetChanged()
            viewModel.setReinitialiseFalse()
        } else if (newSize > oldSize) {
            // Добавление новых элементов (пагинация)
            properties = newList
            notifyItemRangeInserted(oldSize, newSize - oldSize)
        } else if (newSize != oldSize) {
            // На случай других изменений в списке
            properties = newList
            notifyDataSetChanged()
        }
    }
}