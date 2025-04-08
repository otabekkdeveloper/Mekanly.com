package com.mekanly.presentation.ui.bottomSheet

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.mekanly.R
import com.mekanly.data.BottomSheetItem
import com.mekanly.data.responseBody.DataHouseCategory
import com.mekanly.databinding.FragmentBottomSheetBinding
import com.mekanly.presentation.ui.adapters.BottomSheetAdapter
import com.mekanly.presentation.ui.fragments.flow.VMFlow

class SectionSelectionBottomSheet(
    private val onDelete : ()-> Unit
) : BottomSheetDialogFragment() {
    private lateinit var binding: FragmentBottomSheetBinding
    private val vmFlow:VMFlow by activityViewModels()
    private var onCitySelected: ((DataHouseCategory) -> Unit)? = null

    fun setOnCitySelectedListener(listener: (DataHouseCategory) -> Unit) {
        onCitySelected = listener
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
        val categories = vmFlow.globalState.value.categoryHouses
        binding.bottomSheetRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.bottomSheetRecyclerView.adapter = BottomSheetAdapter(categories) { selectedItem ->
            onCitySelected?.invoke(selectedItem)
            dismiss()
        }

        return binding.root
    }
}
