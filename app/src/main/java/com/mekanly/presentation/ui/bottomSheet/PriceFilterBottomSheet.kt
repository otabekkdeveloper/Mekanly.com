package com.mekanly.presentation.ui.bottomSheet

import android.annotation.SuppressLint
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

class PriceFilterBottomSheet(private val onPriceSelected: (String, String) -> Unit) :
    BottomSheetDialogFragment() {

    private lateinit var binding: BottomSheetPriceFilterBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = BottomSheetPriceFilterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding.apply {

            btnClose.setOnClickListener {
                dismiss()
            }

            deleteText.setOnClickListener {
                etMaxPrice.text.clear()
                etMinPrice.text.clear()
            }


        }






        binding.btnConfirm.setOnClickListener {
            val minPrice = formatPrice(binding.etMinPrice.text.toString().trim())
            val maxPrice = formatPrice(binding.etMaxPrice.text.toString().trim())


//            if (minPrice.isEmpty() && maxPrice.isEmpty()) {
//                Toast.makeText(requireContext(), "Siz hiç zat ýazmadyňyz!", Toast.LENGTH_SHORT).show()
//                binding.etMinPrice.error = "Bu ýeri dolduryň"
//                binding.etMaxPrice.error = "Bu ýeri dolduryň"
//                return@setOnClickListener
//            }

//            if (minPrice.isEmpty()) {
//                binding.etMinPrice.error = "Bu ýeri dolduryň"
//                return@setOnClickListener
//            }
//
//            if (maxPrice.isEmpty()) {
//                binding.etMaxPrice.error = "Bu ýeri dolduryň"
//                return@setOnClickListener
//            }





            onPriceSelected(minPrice, maxPrice)
            dismiss()
        }



    }


    @SuppressLint("DefaultLocale")
    private fun formatPrice(price: String): String {
        return try {
            val value = price.toLong()
            when {
                value >= 1_000_000 -> String.format("%.1f mln", value / 1_000_000.0)
                value >= 1_000 -> String.format("%.1f müň", value / 1_000.0)
                else -> price
            }
        } catch (e: NumberFormatException) {
            price
        }
    }



}
