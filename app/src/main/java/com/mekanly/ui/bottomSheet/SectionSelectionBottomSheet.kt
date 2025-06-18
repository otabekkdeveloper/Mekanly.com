package com.mekanly.ui.bottomSheet

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.mekanly.data.models.HouseCategory
import com.mekanly.databinding.FragmentBottomSheetBinding
import com.mekanly.presentation.ui.adapters.BottomSheetAdapter

class SectionSelectionBottomSheet(
    private val categories: List<HouseCategory>,
    private val onDelete : ()-> Unit,
    private var onCategorySelected: ((HouseCategory) -> Unit)? = null
) : BottomSheetDialogFragment() {
    private lateinit var binding: FragmentBottomSheetBinding


    fun setOnCategorySelectedListener(listener: (HouseCategory) -> Unit) {
        onCategorySelected = listener
    }

    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentBottomSheetBinding.inflate(inflater, container, false)

        binding.deleteText.setOnClickListener {
            onDelete()
            dismiss()
        }

        binding.btnClose.setOnClickListener{
            dismiss()
        }

        binding.bottomSheetRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.bottomSheetRecyclerView.adapter = BottomSheetAdapter(categories) { selectedItem ->
            onCategorySelected?.invoke(selectedItem)
            dismiss()
        }

        return binding.root
    }
}
