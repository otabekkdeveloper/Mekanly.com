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
                // Если текущая кнопка еще не выбрана
                if (selectedButton != button) {
                    // Сбрасываем предыдущую выбранную кнопку
                    selectedButton?.setBackgroundResource(R.drawable.emlakler_btn_bg)

                    // Устанавливаем новую выбранную кнопку
                    selectedButton = button
                    button.setBackgroundResource(R.drawable.selected_emlakler_btn_bg)
                }


            }
        }

        return binding.root
    }
}

