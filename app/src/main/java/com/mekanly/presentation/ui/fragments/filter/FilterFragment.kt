package com.mekanly.presentation.ui.fragments.filter

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
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
    ): View {
        binding = FragmentFilterBinding.inflate(inflater, container, false)



        initListeners()
        chipGroups()












        return binding.root
    }


    private fun initListeners() {
        binding.buttonBolum.setOnClickListener {
            val bottomSheet = SectionSelectionBottomSheet()

            bottomSheet.setOnCitySelectedListener { selectedCity ->
                binding.bolumTextView.text = selectedCity

            }

            bottomSheet.show(childFragmentManager, "CustomBottomSheet")
        }

        binding.back.setOnClickListener() {
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
            findNavController().navigate(R.id.action_filterFragment_to_fragmentLocation)
        }
    }


    private fun chipGroups() {
        setupChipGroup(binding.chipGroup, R.id.allChips)
        setupChipGroup(binding.chipGroupTwo, R.id.allChipsTwo)
    }

    private fun setupChipGroup(chipGroup: ChipGroup, allChipsId: Int) {
        val allChips = chipGroup.findViewById<Chip>(allChipsId)

        // Устанавливаем "Все" как выбранный по умолчанию
        allChips.isSelected = true
        allChips.chipBackgroundColor =
            ContextCompat.getColorStateList(requireContext(), R.color.black)
        allChips.setTextColor(ContextCompat.getColor(requireContext(), R.color.white))

        for (i in 0 until chipGroup.childCount) {
            val chip = chipGroup.getChildAt(i) as Chip

            chip.setOnClickListener {
                if (chip.id == allChipsId) {
                    // Если выбран чип "Все"
                    if (!chip.isSelected) {
                        activateChip(allChips, true)

                        // Сбрасываем состояние всех остальных чипов
                        for (j in 0 until chipGroup.childCount) {
                            val otherChip = chipGroup.getChildAt(j) as Chip
                            if (otherChip.id != allChipsId) {
                                activateChip(otherChip, false)
                            }
                        }
                    }
                } else {
                    // Если выбран любой другой чип
                    chip.isSelected = !chip.isSelected
                    activateChip(chip, chip.isSelected)

                    // Проверяем, нужно ли сбросить чип "Все"
                    if (chip.isSelected) {
                        activateChip(allChips, false)
                    } else {
                        // Если ни один другой чип не выбран, активируем чип "Все"
                        if (isNoChipSelected(chipGroup, allChipsId)) {
                            activateChip(allChips, true)
                        }
                    }
                }
            }
        }


    }

    // Функция для активации/деактивации чипа
    private fun activateChip(chip: Chip, isActive: Boolean) {
        chip.isSelected = isActive
        chip.chipBackgroundColor = ContextCompat.getColorStateList(
            requireContext(), if (isActive) R.color.black else R.color.white
        )
        chip.setTextColor(
            ContextCompat.getColor(requireContext(), if (isActive) R.color.white else R.color.black)
        )
        chip.chipStrokeColor = ContextCompat.getColorStateList(requireContext(), R.color.black)
    }

    // Проверяет, выбран ли хотя бы один чип (кроме чипа "Все")
    private fun isNoChipSelected(chipGroup: ChipGroup, allChipsId: Int): Boolean {
        for (i in 0 until chipGroup.childCount) {
            val chip = chipGroup.getChildAt(i) as Chip
            if (chip.id != allChipsId && chip.isSelected) {
                return false
            }
        }
        return true
    }


    @SuppressLint("UseCompatLoadingForDrawables")
    private fun showCustomDialog() {
        val dialogView =
            LayoutInflater.from(requireContext()).inflate(R.layout.fragment_dialog_properties, null)

        val dialog = AlertDialog.Builder(requireContext()).setView(dialogView).create()

        val btnGoybolsun = dialogView.findViewById<Button>(R.id.btnGoybolsun)
        val btnKabulEt = dialogView.findViewById<Button>(R.id.btnKabulEt)

        val buttons = listOf<LinearLayout>(
            dialogView.findViewById(R.id.btnKwartira),
            dialogView.findViewById(R.id.btnKottej),
            dialogView.findViewById(R.id.btnElitka),
            dialogView.findViewById(R.id.btnPolElitka),
            dialogView.findViewById(R.id.btnDacha),
            dialogView.findViewById(R.id.btnPlanJay)
        )

        val cbHemmesi = dialogView.findViewById<CheckBox>(R.id.cbHemmesi)

        fun toggleAllButtons() {
            if (cbHemmesi.isChecked) {
                buttons.forEach { button ->
                    button.setBackgroundResource(R.drawable.bg_selected_properties_btn)
                }
            } else {
                buttons.forEach { button ->
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

                if (buttons.all { it.background.constantState == requireContext().getDrawable(R.drawable.bg_selected_properties_btn)?.constantState }) {
                    cbHemmesi.isChecked = true
                }

                if (buttons.any {
                        it.background.constantState != requireContext().getDrawable(R.drawable.bg_selected_properties_btn)?.constantState
                    }) {
                    cbHemmesi.isChecked = false
                }

            }

        }

        cbHemmesi.setOnClickListener {
            toggleAllButtons()
        }

        btnGoybolsun.setOnClickListener {
            Toast.makeText(requireContext(), "Отмена", Toast.LENGTH_SHORT).show()
            dialog.dismiss()
        }

        btnKabulEt.setOnClickListener {
            Toast.makeText(requireContext(), "Принято", Toast.LENGTH_SHORT).show()
            //
            dialog.dismiss()
        }

        dialog.show()
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

        val cbHemmesi = dialogView.findViewById<CheckBox>(R.id.cbHemmesi)


        fun toggleAllButtons() {
            buttons.forEach { button ->
                if (cbHemmesi.isChecked) {
                    button.setBackgroundResource(R.drawable.bg_selected_properties_btn)
                } else {
                    button.setBackgroundResource(R.drawable.emlakler_btn_bg)
                }
            }
        }



        buttons.forEach { button ->
            button.setOnClickListener {

                if (button.background.constantState == requireContext().getDrawable(R.drawable.bg_selected_properties_btn)?.constantState) {
                    button.setBackgroundResource(R.drawable.emlakler_btn_bg)
                } else {

                    button.setBackgroundResource(R.drawable.bg_selected_properties_btn)
                }


                if (buttons.any {
                        it.background.constantState != requireContext().getDrawable(R.drawable.bg_selected_properties_btn)?.constantState
                    }) {
                    cbHemmesi.isChecked = false
                }


                if (buttons.all {
                        it.background.constantState == requireContext().getDrawable(R.drawable.bg_selected_properties_btn)?.constantState
                    }) {
                    cbHemmesi.isChecked = true
                }
            }
        }

        cbHemmesi.setOnClickListener {
            toggleAllButtons()
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

    @SuppressLint(
        "UseCompatLoadingForDrawables", "MissingInflatedId", "InflateParams",
        "CutPasteId"
    )
    private fun OpportunityDialog() {
        val dialogView = LayoutInflater.from(requireContext())
            .inflate(R.layout.fragment_dialog_mumkinchilikler, null)
//
//        val recyclerView = dialogView.findViewById<RecyclerView>(R.id.recyclerView)
//        recyclerView.layoutManager = GridLayoutManager(requireContext(), 2) // Сетка с 2 столбцами

        //        val adapter = OpportunityDialogAdapter(items) { item ->
//            Toast.makeText(requireContext(), "${item.text} clicked", Toast.LENGTH_SHORT).show()
//        }
//
//        recyclerView.adapter = adapter


// Получаем родительский ConstraintLayout


        // Создаём и отображаем диалог
        AlertDialog.Builder(requireContext())
            .setView(dialogView)
            .setCancelable(true)
            .create()
            .show()
    }

}




