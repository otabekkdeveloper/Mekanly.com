package com.mekanly

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.mekanly.adapters.BottomSheetAdapter
import com.mekanly.adapters.BottomSheetItem

class BottomSheetFragment : BottomSheetDialogFragment() {

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

        // Список городов
        val cities = listOf(
            BottomSheetItem(R.drawable.satlyk_jaylar_icon, "Satlyk jaýlar"),
            BottomSheetItem(R.drawable.kireyne_jaylar, "Kireýne jaýlar"),
            BottomSheetItem(R.drawable.kireyne_otaglar, "Kireýne otaglar"),
            BottomSheetItem(R.drawable.ofis, "Kireýne ofisler"),
            BottomSheetItem(R.drawable.sowda_emlakler, "Kireýne söwda emläkler"),
            BottomSheetItem(R.drawable.satlyk_jaylar_icon, "Satlyk söwda emläkler"),
            BottomSheetItem(R.drawable.beyleki_emlakler, "Beýleki emläkler")
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
