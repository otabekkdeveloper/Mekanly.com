package com.mekanly.presentation.ui.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.mekanly.R
import com.mekanly.databinding.FragmentDialogEmlaklerBinding

class PropertiesTypeDialog : Fragment() {
    private lateinit var binding: FragmentDialogEmlaklerBinding
    private var selectedButton: View? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentDialogEmlaklerBinding.inflate(inflater, container, false)


        val buttons = with(binding) {
            listOf(
                btnKwartira, btnKottej, btnElitka, btnPolElitka, btnDacha, btnPlanJay
            )
        }

        buttons.forEach { button ->
            button.setOnClickListener {
                handleButtonClick(button)
            }
        }

        return binding.root
    }

    private fun handleButtonClick(button: View) {
        if (selectedButton == button) {
            // Если нажали на ту же кнопку дважды, сбрасываем фон
            button.setBackgroundResource(R.drawable.emlakler_btn_bg)
            selectedButton = null
        } else {
            // Сбрасываем предыдущий выбранный фон
            selectedButton?.setBackgroundResource(R.drawable.emlakler_btn_bg)

            // Устанавливаем фон для новой выбранной кнопки
            button.setBackgroundResource(R.drawable.bg_selected_properties_btn)
            selectedButton = button
        }
    }
}

