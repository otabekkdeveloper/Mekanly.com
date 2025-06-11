package com.mekanly.presentation.ui.fragments.businessProfile.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mekanly.data.models.BusinessProfile
import com.mekanly.databinding.ItemBusinessProfileBinding
import com.mekanly.presentation.ui.fragments.businessProfile.viewModel.VMBusinessProfiles

class AdapterBusinessProfilesPaginated(
    private val viewModel: VMBusinessProfiles,
    private val navController: NavController
) : RecyclerView.Adapter<AdapterBusinessProfilesPaginated.BusinessProfileViewHolder>() {

    private val businessProfiles = mutableListOf<BusinessProfile>()

    inner class BusinessProfileViewHolder(private val binding: ItemBusinessProfileBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(profile: BusinessProfile) {
            Log.d("AdapterBind", "Binding item: ${profile.brand}")
            binding.apply {
                tvPropertyType.text = profile.brand
//                tvDescription.text = profile.description
                Glide.with(imgProperty.context)
                    .load(profile.image)
                    .into(imgProperty)

                root.setOnClickListener {
                    // val action = FragmentFlowDirections.actionToBusinessProfileDetail(profile.id)
                    // navController.navigate(action)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BusinessProfileViewHolder {
        val binding = ItemBusinessProfileBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return BusinessProfileViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BusinessProfileViewHolder, position: Int) {
        holder.bind(businessProfiles[position])
    }

    override fun getItemCount(): Int = businessProfiles.size

    fun updateList(newList: List<BusinessProfile>) {
        val oldSize = businessProfiles.size
        businessProfiles.clear()
        businessProfiles.addAll(newList)
        notifyDataSetChanged() // Попробуем начать с полного обновления
    }
}
