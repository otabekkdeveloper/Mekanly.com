package com.mekanly.presentation.ui.bottomSheet

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.mekanly.R
import com.mekanly.databinding.BottomSheetPriceFilterBinding

class PriceFilterBottomSheet : BottomSheetDialogFragment() {
    private lateinit var binding: BottomSheetPriceFilterBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {

        binding = BottomSheetPriceFilterBinding.inflate(inflater, container, false)


        binding.apply {

            btnClose.setOnClickListener{
                dismiss()
            }

            deleteText.setOnClickListener{
                etMaxPrice.text.clear()
                etMinPrice.text.clear()

            }

            btnConfirm.setOnClickListener {
                val min = etMinPrice.text.toString()
                val max = etMaxPrice.text.toString()

                Toast.makeText(requireContext(), "Min: $min, Max: $max", Toast.LENGTH_SHORT).show()
                dismiss()
            }

        }



        return binding.root
    }

}
