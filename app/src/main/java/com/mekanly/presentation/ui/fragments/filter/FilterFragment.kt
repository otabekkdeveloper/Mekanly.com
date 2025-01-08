package com.mekanly.presentation.ui.fragments.filter

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.mekanly.R
import com.mekanly.databinding.FragmentFilterBinding
import com.mekanly.presentation.ui.bottomSheet.SectionSelectionBottomSheet


class FilterFragment : Fragment() {
    private lateinit var binding: FragmentFilterBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFilterBinding.inflate(inflater, container, false)


        val chipGroup = binding.chipGroup
        val chipGroupTwo = binding.chipGroupTwo

        // Проходим по всем Chip в ChipGroup
        for (i in 0 until chipGroup.childCount) {
            val chip = chipGroup.getChildAt(i) as Chip

            // Обрабатываем нажатие на каждый Chip
            chip.setOnClickListener {
                updateChipStyles(chipGroup, chip)
            }
        }

        // Проходим по всем Chip в ChipGroup
        for (i in 0 until chipGroup.childCount) {
            val chip = chipGroup.getChildAt(i) as Chip

            // Обрабатываем нажатие на каждый Chip
            chip.setOnClickListener {
                if (!chip.isChecked) {
                    updateChipStyles(chipGroupTwo, chip) // Сброс всех чипов
                    chip.isChecked = true // Выделяем текущий чип
                } else {
                    chip.isChecked = false // Сбрасываем состояние текущего чипа
                }
            }
        }



        binding.buttonBolum.setOnClickListener {
            val bottomSheet = SectionSelectionBottomSheet()

            bottomSheet.setOnCitySelectedListener { selectedCity ->
                binding.bolumTextView.text = selectedCity

            }


            bottomSheet.show(childFragmentManager, "CustomBottomSheet")
        }

        binding.backBtn.setOnClickListener {
            findNavController().popBackStack()
        }


        binding.exampleBtn.setOnClickListener {
            showCustomDialog()
        }

        binding.remont.setOnClickListener {
            RemontDialog()
        }

        binding.mumkinchilikler.setOnClickListener {
            OpportunityDialog()
        }

        binding.location.setOnClickListener {
            LocationDialog()
        }

//        for (i in 0 until binding.chipGroup.childCount){
//            val chip = binding.chipGroup.getChildAt(i) as Chip
//            var isDoubleTap = false
//
//            chip.setOnClickListener{
//                chip.isChecked = !isDoubleTap
//
//
//                }
//
//        }


        return binding.root
    }


    @SuppressLint("UseCompatLoadingForDrawables")
    private fun showCustomDialog() {
        val dialogView =
            LayoutInflater.from(requireContext()).inflate(R.layout.fragment_dialog_properties, null)

        val dialog = AlertDialog.Builder(requireContext()).setView(dialogView).create()

        val btnGoybolsun = dialogView.findViewById<Button>(R.id.btnGoybolsun)
        val btnKabulEt = dialogView.findViewById<Button>(R.id.btnKabulEt)

        // Список кнопок
        val buttons = listOf<LinearLayout>(
            dialogView.findViewById(R.id.btnKwartira),
            dialogView.findViewById(R.id.btnKottej),
            dialogView.findViewById(R.id.btnElitka),
            dialogView.findViewById(R.id.btnPolElitka),
            dialogView.findViewById(R.id.btnDacha),
            dialogView.findViewById(R.id.btnPlanJay)
        )

        // Чекбокс "Выбрать все"
        val cbHemmesi = dialogView.findViewById<CheckBox>(R.id.cbHemmesi)

        // Функция для выбора/отмены выбора всех кнопок
        fun toggleAllButtons(selectAll: Boolean) {
            buttons.forEach { button ->
                if (selectAll) {
                    button.setBackgroundResource(R.drawable.bg_selected_properties_btn)
                }
                else {
                    button.setBackgroundResource(R.drawable.emlakler_btn_bg)
                }
            }
        }


        buttons.forEach { button ->
            button.setOnClickListener {
                if (button.background.constantState == requireContext().getDrawable(R.drawable.bg_selected_properties_btn)?.constantState) {

                    button.setBackgroundResource((R.drawable.emlakler_btn_bg))
                } else {
                    button.setBackgroundResource(R.drawable.bg_selected_properties_btn)
                }

                if (buttons.all {
                    it.background.constantState == requireContext().getDrawable(R.drawable.bg_selected_properties_btn)?.constantState
                    }) {
                    cbHemmesi.isChecked = true
                }

                if (buttons.all {
                        it.background.constantState != requireContext().getDrawable(R.drawable.bg_selected_properties_btn)?.constantState
                    }) {
                    cbHemmesi.isChecked = false
                }

            }

        }




        // Установка обработчика для всех кнопок
//        buttons.forEach { button ->
//            button.setOnClickListener {
//                // Проверяем текущее состояние кнопки
//                if (button.background.constantState == requireContext().getDrawable(R.drawable.bg_selected_properties_btn)?.constantState) {
//                    // Если уже выбрано, возвращаем стандартный фон
//                    button.setBackgroundResource(R.drawable.emlakler_btn_bg)
//                } else {
//                    // Если не выбрано, устанавливаем фон "выбрано"
//                    button.setBackgroundResource(R.drawable.bg_selected_properties_btn)
//                }
//
//                // Если хотя бы одна кнопка не выбрана, отключаем "Выбрать все"
//                if (buttons.any {
//                        it.background.constantState != requireContext().getDrawable(R.drawable.bg_selected_properties_btn)?.constantState
//                    }) {
//                    cbHemmesi.isChecked = false
//                }
//
//                // Если все кнопки выбраны, включаем "Выбрать все"
//                if (buttons.all {
//                        it.background.constantState == requireContext().getDrawable(R.drawable.bg_selected_properties_btn)?.constantState
//                    }) {
//                    cbHemmesi.isChecked = true
//                }
//            }
//        }

        // Обработчик для чекбокса "Выбрать все"
        cbHemmesi.setOnCheckedChangeListener { _, isChecked ->
            toggleAllButtons(isChecked)
        }

        btnGoybolsun.setOnClickListener {
            Toast.makeText(requireContext(), "Отмена", Toast.LENGTH_SHORT).show()
            dialog.dismiss()
        }

        btnKabulEt.setOnClickListener {
            Toast.makeText(requireContext(), "Принято", Toast.LENGTH_SHORT).show()
            dialog.dismiss()
        }

        dialog.show()
    }

    fun countSelectedButtons(buttons: List<LinearLayout>, context: Context): Int {
        var count = 0
        for (button in buttons) {
            val background = button.background
            if (background != null) {
                val drawableId = (background as? android.graphics.drawable.BitmapDrawable)?.bitmap
                val selectedDrawable =
                    context.getDrawable(R.drawable.bg_selected_properties_btn) as? android.graphics.drawable.BitmapDrawable

                if (selectedDrawable?.bitmap == drawableId) {
                    count++
                }
            }
        }
        return count
    }


    @SuppressLint("CutPasteId", "UseCompatLoadingForDrawables")
    private fun RemontDialog() {
        // Инфлейтим кастомный макет диалога
        val dialogView =
            LayoutInflater.from(requireContext()).inflate(R.layout.fragment_dialog_remont, null)

        // Создаем диалог
        val dialog = AlertDialog.Builder(requireContext()).setView(dialogView).create()

        // Настраиваем кнопки
        val btnGoybolsun = dialogView.findViewById<Button>(R.id.btnGoybolsun)
        val btnKabulEt = dialogView.findViewById<Button>(R.id.btnKabulEt)


        val buttons = listOf<TextView>(
            dialogView.findViewById(R.id.euro_remont),
            dialogView.findViewById(R.id.remont_cosmetic),
            dialogView.findViewById(R.id.remont_designer),
            dialogView.findViewById(R.id.gos_remont),
            dialogView.findViewById(R.id.normal_remont),
            dialogView.findViewById(R.id.remont_etmeli)
        )


        // Чекбокс "Выбрать все"
        val cbHemmesi = dialogView.findViewById<CheckBox>(R.id.cbHemmesi)

        // Функция для выбора/отмены выбора всех кнопок
        fun toggleAllButtons(selectAll: Boolean) {
            buttons.forEach { button ->
                if (selectAll) {
                    button.setBackgroundResource(R.drawable.bg_selected_properties_btn)
                } else {
                    button.setBackgroundResource(R.drawable.emlakler_btn_bg)
                }
            }
        }

        // Установка обработчика для всех кнопок
        buttons.forEach { button ->
            button.setOnClickListener {
                // Проверяем текущее состояние кнопки
                if (button.background.constantState == requireContext().getDrawable(R.drawable.bg_selected_properties_btn)?.constantState) {
                    // Если уже выбрано, возвращаем стандартный фон
                    button.setBackgroundResource(R.drawable.emlakler_btn_bg)
                } else {
                    // Если не выбрано, устанавливаем фон "выбрано"
                    button.setBackgroundResource(R.drawable.bg_selected_properties_btn)
                }

                // Если хотя бы одна кнопка не выбрана, отключаем "Выбрать все"
                if (buttons.any {
                        it.background.constantState != requireContext().getDrawable(R.drawable.bg_selected_properties_btn)?.constantState
                    }) {
                    cbHemmesi.isChecked = false
                }

                // Если все кнопки выбраны, включаем "Выбрать все"
                if (buttons.all {
                        it.background.constantState == requireContext().getDrawable(R.drawable.bg_selected_properties_btn)?.constantState
                    }) {
                    cbHemmesi.isChecked = true
                }
            }
        }

        // Обработчик для чекбокса "Выбрать все"
        cbHemmesi.setOnCheckedChangeListener { _, isChecked ->
            toggleAllButtons(isChecked)
        }


        btnGoybolsun.setOnClickListener {
            Toast.makeText(requireContext(), "Отмена", Toast.LENGTH_SHORT).show()
            dialog.dismiss()
        }

        btnKabulEt.setOnClickListener {
            Toast.makeText(requireContext(), "Принято", Toast.LENGTH_SHORT).show()
            dialog.dismiss()
        }


        dialog.show()
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private fun OpportunityDialog() {
        // Инфлейтим кастомный макет диалога
        val dialogView = LayoutInflater.from(requireContext())
            .inflate(R.layout.fragment_dialog_mumkinchilikler, null)

        // Создаем диалог
        val dialog = AlertDialog.Builder(requireContext()).setView(dialogView).create()


        val buttons = listOf<Button>(
            dialogView.findViewById(R.id.wifi),
            dialogView.findViewById(R.id.kitchen),
            dialogView.findViewById(R.id.tv),
            dialogView.findViewById(R.id.refrigerator),
            dialogView.findViewById(R.id.washing_machine),
            dialogView.findViewById(R.id.conditioner),
            dialogView.findViewById(R.id.bedroom),
            dialogView.findViewById(R.id.furniture),
            dialogView.findViewById(R.id.bathroom),
            dialogView.findViewById(R.id.filtration_system),
            dialogView.findViewById(R.id.oven),
            dialogView.findViewById(R.id.grill),
            dialogView.findViewById(R.id.hot_water),
            dialogView.findViewById(R.id.working_table),
            dialogView.findViewById(R.id.kitchen_furniture),
            dialogView.findViewById(R.id.swimming_pool),
            dialogView.findViewById(R.id.balcony),
            dialogView.findViewById(R.id.elevator)
        )

        // Чекбокс "Выбрать все"
        val cbHemmesi = dialogView.findViewById<CheckBox>(R.id.cbHemmesi)

        // Функция для выбора/отмены выбора всех кнопок
        fun toggleAllButtons(selectAll: Boolean) {
            buttons.forEach { button ->
                if (selectAll) {
                    button.setBackgroundResource(R.drawable.bg_selected_properties_btn)
                } else {
                    button.setBackgroundResource(R.drawable.emlakler_btn_bg)
                }
            }
        }

        // Установка обработчика для всех кнопок
        buttons.forEach { button ->
            button.setOnClickListener {
                // Проверяем текущее состояние кнопки
                if (button.background.constantState == requireContext().getDrawable(R.drawable.bg_selected_properties_btn)?.constantState) {
                    // Если уже выбрано, возвращаем стандартный фон
                    button.setBackgroundResource(R.drawable.emlakler_btn_bg)
                } else {
                    // Если не выбрано, устанавливаем фон "выбрано"
                    button.setBackgroundResource(R.drawable.bg_selected_properties_btn)
                }

                // Если хотя бы одна кнопка не выбрана, отключаем "Выбрать все"
                if (buttons.any {
                        it.background.constantState != requireContext().getDrawable(R.drawable.bg_selected_properties_btn)?.constantState
                    }) {
                    cbHemmesi.isChecked = false
                }

                // Если все кнопки выбраны, включаем "Выбрать все"
                if (buttons.all {
                        it.background.constantState == requireContext().getDrawable(R.drawable.bg_selected_properties_btn)?.constantState
                    }) {
                    cbHemmesi.isChecked = true
                }
            }
        }

        // Обработчик для чекбокса "Выбрать все"
        cbHemmesi.setOnCheckedChangeListener { _, isChecked ->
            toggleAllButtons(isChecked)
        }


        // Настраиваем кнопки
        val btnGoybolsun = dialogView.findViewById<Button>(R.id.btnGoybolsun)
        val btnKabulEt = dialogView.findViewById<Button>(R.id.btnKabulEt)

        btnGoybolsun.setOnClickListener {
            Toast.makeText(requireContext(), "Отмена", Toast.LENGTH_SHORT).show()
            dialog.dismiss()
        }

        btnKabulEt.setOnClickListener {
            Toast.makeText(requireContext(), "Принято", Toast.LENGTH_SHORT).show()
            dialog.dismiss()
        }



        dialog.show()
    }

    private fun LocationDialog() {
        // Инфлейтим кастомный макет диалога
        val dialogView =
            LayoutInflater.from(requireContext()).inflate(R.layout.fragment_location_dialog, null)

        // Инициализируем чекбоксы
        val checkBoxCity1 = dialogView.findViewById<CheckBox>(R.id.checkbox_city1)
        val checkBoxCity2 = dialogView.findViewById<CheckBox>(R.id.checkbox_city2)
        val checkBoxCity3 = dialogView.findViewById<CheckBox>(R.id.checkbox_city3)
        val checkBoxCity4 = dialogView.findViewById<CheckBox>(R.id.checkbox_city4)
        val checkBoxCity5 = dialogView.findViewById<CheckBox>(R.id.checkbox_city5)
        val checkBoxCity6 = dialogView.findViewById<CheckBox>(R.id.checkbox_city6)
        val checkBoxCity7 = dialogView.findViewById<CheckBox>(R.id.checkbox_city7)
        val checkBoxSelectAll = dialogView.findViewById<CheckBox>(R.id.checkbox_select_all)


        // Создаем диалог
        val dialog = AlertDialog.Builder(requireContext()).setView(dialogView).create()

        // Логика для кнопок
        val btnGoybolsun = dialogView.findViewById<Button>(R.id.btnGoybolsun)
        val btnKabulEt = dialogView.findViewById<Button>(R.id.btnKabulEt)

        btnGoybolsun.setOnClickListener {
            Toast.makeText(requireContext(), "Отмена", Toast.LENGTH_SHORT).show()
            dialog.dismiss()
        }

        btnKabulEt.setOnClickListener {
            // Собираем выбранные города
            val selectedCities = mutableListOf<String>()
            if (checkBoxCity1.isChecked) selectedCities.add(checkBoxCity1.text.toString())
            if (checkBoxCity2.isChecked) selectedCities.add(checkBoxCity2.text.toString())
            if (checkBoxCity3.isChecked) selectedCities.add(checkBoxCity3.text.toString())
            if (checkBoxCity4.isChecked) selectedCities.add(checkBoxCity4.text.toString())
            if (checkBoxCity5.isChecked) selectedCities.add(checkBoxCity5.text.toString())
            if (checkBoxCity6.isChecked) selectedCities.add(checkBoxCity6.text.toString())
            if (checkBoxCity7.isChecked) selectedCities.add(checkBoxCity7.text.toString())

            if (checkBoxSelectAll.isChecked) {
                selectedCities.clear()
                selectedCities.add(selectedCities.toString())
            }

            // Отображаем выбранные города в TextView
            binding.locationText.text = selectedCities.joinToString(", ")

            dialog.dismiss()
        }

        // Логика для чекбокса "Выбрать все"
        checkBoxSelectAll.setOnCheckedChangeListener { _, isChecked ->
            checkBoxCity1.isChecked = isChecked
            checkBoxCity2.isChecked = isChecked
            checkBoxCity3.isChecked = isChecked
            checkBoxCity4.isChecked = isChecked
            checkBoxCity5.isChecked = isChecked
            checkBoxCity6.isChecked = isChecked
            checkBoxCity7.isChecked = isChecked
        }

        dialog.show()
    }


    // Метод для обновления стилей
    private fun updateChipStyles(chipGroup: ChipGroup, selectedChip: Chip) {
        // Сбрасываем стили для всех Chip
        for (i in 0 until chipGroup.childCount) {
            val chip = chipGroup.getChildAt(i) as Chip
        }

//         Изменяем стиль выбранного Chip
        selectedChip.setTextColor(resources.getColor(R.color.white, null))
        selectedChip.setChipBackgroundColorResource(R.color.black)
        selectedChip.setChipStrokeColorResource(R.color.white)
    }


}

