package com.mekanly.presentation.ui.bottomSheet

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.mekanly.R
import com.mekanly.data.BottomSheetItem
import com.mekanly.databinding.FragmentBottomSheetBinding
import com.mekanly.presentation.ui.adapters.BottomSheetAdapter

class SectionSelectionBottomSheet(
    private val onDelete : ()-> Unit
) : BottomSheetDialogFragment() {
    private lateinit var binding: FragmentBottomSheetBinding

    private var onCitySelected: ((String) -> Unit)? = null

    fun setOnCitySelectedListener(listener: (String) -> Unit) {
        onCitySelected = listener
    }

    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentBottomSheetBinding.inflate(inflater, container, false)

        binding.deleteText.setOnClickListener {
            onDelete() // Вызываем функцию удаления
            dismiss()
        }



        binding.btnClose.setOnClickListener{
            dismiss()
        }


        val cities = listOf(
            BottomSheetItem(R.drawable.ic_houses_for_sale, "Satlyk jaýlar"),
            BottomSheetItem(R.drawable.kireyne_jaylar, "Kireýne jaýlar"),
            BottomSheetItem(R.drawable.kireyne_otaglar, "Kireýne otaglar"),
            BottomSheetItem(R.drawable.ofis, "Kireýne ofisler"),
            BottomSheetItem(R.drawable.ic_commercial_properties, "Kireýne söwda emläkler"),
            BottomSheetItem(R.drawable.ic_houses_for_sale, "Satlyk söwda emläkler"),
            BottomSheetItem(R.drawable.ic_other_properties, "Beýleki emläkler")
        )


        binding.bottomSheetRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.bottomSheetRecyclerView.adapter = BottomSheetAdapter(cities) { selectedItem ->
            onCitySelected?.invoke(selectedItem.title) // Передаем название выбранного города
            dismiss() // Закрываем BottomSheet
        }

        return binding.root
    }
}
