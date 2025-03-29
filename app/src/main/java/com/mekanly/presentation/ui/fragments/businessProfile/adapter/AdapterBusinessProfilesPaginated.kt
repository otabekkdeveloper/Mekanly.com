package com.mekanly.presentation.ui.fragments.businessProfile.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mekanly.data.dataModels.DataBusinessProfile
import com.mekanly.data.dataModels.DataHouse
import com.mekanly.data.repository.RepositoryHouses.Companion.LIMIT_REGULAR
import com.mekanly.databinding.ItemBusinessProfileBinding
import com.mekanly.presentation.ui.fragments.businessProfile.viewModel.VMBusinessProfiles
import com.mekanly.presentation.ui.fragments.flow.FragmentFlowDirections
import com.mekanly.presentation.ui.fragments.search.viewModel.VMSearch

class AdapterBusinessProfilesPaginated(
    private var businessProfiles: List<DataBusinessProfile>,
    private val viewModel: VMBusinessProfiles,
    private val navController: NavController
) : RecyclerView.Adapter<AdapterBusinessProfilesPaginated.BusinessProfileViewHolder>() {

    inner class BusinessProfileViewHolder(private val binding: ItemBusinessProfileBinding) :
        RecyclerView.ViewHolder(binding.root) {

        @SuppressLint("SetTextI18n")
        fun bind(property: DataBusinessProfile) {
            binding.apply {
                tvPropertyType.text = property.brand
                tvDescription.text = property.description
                Glide.with(imgProperty.context).load(property.image).into(imgProperty)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BusinessProfileViewHolder {
        val binding = ItemBusinessProfileBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return BusinessProfileViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: AdapterBusinessProfilesPaginated.BusinessProfileViewHolder, position: Int
    ) {
        val property = businessProfiles[position]
        holder.bind(property)
    }

    override fun getItemCount() = businessProfiles.size

    fun updateList() {
        val lastPageCount = if (viewModel.businessProfiles.value.size % LIMIT_REGULAR == 0L) {
            LIMIT_REGULAR
        } else {
            viewModel.businessProfiles.value.size % LIMIT_REGULAR
        }
        notifyItemRangeInserted(viewModel.businessProfiles.value.size, lastPageCount.toInt())
    }

    fun onBusinessProfileClicked(item: DataHouse) {
        val action =
            FragmentFlowDirections.actionHomeFragmentToFragmentSingleHouse(item.id.toLong())

        navController.navigate(action)
    }
}
