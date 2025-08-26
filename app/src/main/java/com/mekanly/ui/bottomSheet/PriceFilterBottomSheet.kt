package com.mekanly.presentation.ui.bottomSheet

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.mekanly.databinding.BottomSheetPriceFilterBinding

class PriceFilterBottomSheet(
    private val onPriceSelected: (String, String) -> Unit
) : BottomSheetDialogFragment() {

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
            btnClose.setOnClickListener { dismiss() }

            deleteText.setOnClickListener {
                onPriceSelected("", "")
                dismiss()
            }

            btnConfirm.setOnClickListener {

                val minPriceText = etMinPrice.text.toString().trim()
                val maxPriceText = etMaxPrice.text.toString().trim()

                val minPriceLong = etMinPrice.text.toString().trim().toLongOrNull()
                val maxPriceLong = etMaxPrice.text.toString().trim().toLongOrNull()

                if (minPriceLong != null && maxPriceLong != null && minPriceLong > maxPriceLong) {
                    Toast.makeText(requireContext(), "Ýalňyş! Min baha maks bahadan uly bolmaly däl!", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }

                val minPriceFormatted = parseFormattedPrice(minPriceText)
                val maxPriceFormatted = parseFormattedPrice(maxPriceText)

                onPriceSelected(minPriceFormatted.toString(), maxPriceFormatted.toString())
                dismiss()
            }

        }
    }

    @SuppressLint("DefaultLocale")
    private fun parseFormattedPrice(formattedPrice: String): Int? {
        return try {
            when {
                formattedPrice.isEmpty() -> null
                formattedPrice.contains("mln") -> {
                    val value = formattedPrice.replace("mln", "").trim().toDouble()
                    (value * 1_000_000).toInt()
                }
                formattedPrice.contains("müň") -> {
                    val value = formattedPrice.replace("müň", "").trim().toDouble()
                    (value * 1_000).toInt()
                }
                else -> formattedPrice.toInt()
            }
        } catch (e: NumberFormatException) {
            null
        }
    }
}
