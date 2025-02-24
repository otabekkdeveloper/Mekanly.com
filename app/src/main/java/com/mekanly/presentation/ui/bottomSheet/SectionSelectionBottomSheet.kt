package com.mekanly.presentation.ui.bottomSheet

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.mekanly.R
import com.mekanly.presentation.ui.adapters.BottomSheetAdapter
import com.mekanly.presentation.ui.adapters.BottomSheetItem

class SectionSelectionBottomSheet : BottomSheetDialogFragment() {

    private var onCitySelected: ((String) -> Unit)? = null

    fun setOnCitySelectedListener(listener: (String) -> Unit) {
        onCitySelected = listener
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_bottom_sheet, container, false)
        val recyclerView = view.findViewById<RecyclerView>(R.id.bottomSheetRecyclerView)

        val cities = listOf(
            BottomSheetItem(R.drawable.ic_houses_for_sale, "Satlyk jaýlar"),
            BottomSheetItem(R.drawable.kireyne_jaylar, "Kireýne jaýlar"),
            BottomSheetItem(R.drawable.kireyne_otaglar, "Kireýne otaglar"),
            BottomSheetItem(R.drawable.ofis, "Kireýne ofisler"),
            BottomSheetItem(R.drawable.ic_commercial_properties, "Kireýne söwda emläkler"),
            BottomSheetItem(R.drawable.ic_houses_for_sale, "Satlyk söwda emläkler"),
            BottomSheetItem(R.drawable.ic_other_properties, "Beýleki emläkler")
        )

        // Настройка адаптера
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = BottomSheetAdapter(cities) { selectedItem ->
            onCitySelected?.invoke(selectedItem.title) // Передаем название выбранного города
            dismiss() // Закрываем BottomSheet
        }

        return view
    }
}
