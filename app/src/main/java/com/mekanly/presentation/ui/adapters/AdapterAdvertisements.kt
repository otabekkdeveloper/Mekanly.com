package com.mekanly.presentation.ui.adapters

import android.annotation.SuppressLint
import android.view.GestureDetector
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.recyclerview.widget.RecyclerView
import com.mekanly.R
import com.mekanly.data.dataModels.DataHouse
import com.mekanly.data.repository.RepositoryHouses.Companion.LIMIT_REGULAR
import com.mekanly.databinding.ItemAdvBigBinding
import com.mekanly.presentation.ui.fragments.flow.FragmentFlowDirections
import com.mekanly.presentation.ui.fragments.search.ImageSliderAdapter
import com.mekanly.presentation.ui.fragments.search.viewModel.VMSearch

class AdapterAdvertisements(
    private var properties: List<DataHouse>,
    private val viewModel: VMSearch,
    private val navController: NavController
) : RecyclerView.Adapter<AdapterAdvertisements.PropertyViewHolder>() {

    inner class PropertyViewHolder(private val binding: ItemAdvBigBinding) :
        RecyclerView.ViewHolder(binding.root) {

        @SuppressLint("SetTextI18n", "ClickableViewAccessibility")
        fun bind(property: DataHouse) {
            binding.item = property
            binding.apply {
                tvMainTitle.text = property.name
                tvPrice.text = "${property.price} TMT"
                tvAddressTime.text = "${property.location.parent_name}, ${property.location.name}"
                tvDescription.text = property.description
                advType.text = property.categoryName

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

    fun updateList() {
        val lastPageCount = if (viewModel.houses.value.size % LIMIT_REGULAR == 0L) {
            LIMIT_REGULAR
        } else {
            viewModel.houses.value.size % LIMIT_REGULAR
        }
        notifyItemRangeInserted(viewModel.houses.value.size, lastPageCount.toInt())
    }

//    fun onAdvClicked(item: DataHouse) {
//        val action =
//            FragmentFlowDirections.actionHomeFragmentToFragmentSingleHouse(item.id.toLong())
//
//        navController.navigate(action)
//    }
}
