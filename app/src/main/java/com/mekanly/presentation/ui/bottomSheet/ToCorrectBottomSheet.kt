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

class ToCorrectBottomSheet : BottomSheetDialogFragment() {

    private var onCitySelected: ((String) -> Unit)? = null

    fun setOnCitySelectedListener(listener: (String) -> Unit) {
        onCitySelected = listener
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.bottom_sheet_to_correct, container, false)




        return view
    }
}
