package com.mekanly.ui.fragments.filter

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.google.android.material.slider.RangeSlider
import com.mekanly.R
import com.mekanly.data.OpportunityData
import com.mekanly.databinding.FragmentFilterBinding
import com.mekanly.ui.adapters.OpportunityDialogAdapter
import com.mekanly.ui.bottomSheet.SectionSelectionBottomSheet
import com.mekanly.ui.dialog.OptionSelectionDialog


class FilterFragment : Fragment() {
    private lateinit var binding: FragmentFilterBinding
    private var isProgrammaticChange = false


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentFilterBinding.inflate(inflater, container, false)
        initListeners()
        chipGroups()
        switchDesign()
//        seekBarLogicTerritory()
        priceEditText()
        binding.popupMenu.setOnClickListener{ view->

            showPopupMenu(view)

        }


// Инициализация компонентов через View Binding
        val rangeSeekBar = binding.rangeSeekBar
        val minValueText = binding.minValueText
        val maxValueText = binding.maxValueText

        // Установка начальных значений с проверкой
        val initialValues = rangeSeekBar.values
        if (initialValues.size >= 2) {
            minValueText.setText(initialValues[0].toInt().toString())
            maxValueText.setText(initialValues[1].toInt().toString())
        } else {
            // Устанавливаем значения по умолчанию, если список некорректен
            rangeSeekBar.values = listOf(5f, 700f) // Устанавливаем диапазон вручную
            minValueText.setText("5")
            maxValueText.setText("700")
        }

        // Слушатель для изменения RangeSlider
        rangeSeekBar.addOnChangeListener { slider, value, fromUser ->
            if (fromUser && !isProgrammaticChange) {
                isProgrammaticChange = true
                val values = slider.values
                if (values.size >= 2) {
                    minValueText.setText(values[0].toInt().toString())
                    maxValueText.setText(values[1].toInt().toString())
                }
                isProgrammaticChange = false
            }
        }

        // Слушатель для изменения EditText (минимум)
        minValueText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                if (!isProgrammaticChange) {
                    isProgrammaticChange = true
                    val minValue = s.toString().toIntOrNull() ?: 5
                    val maxValue = maxValueText.text.toString().toIntOrNull() ?: 700

                    val clampedMin = minOf(maxOf(minValue, 5), maxValue)
                    rangeSeekBar.values = listOf(clampedMin.toFloat(), maxValue.toFloat())
                    isProgrammaticChange = false
                }
            }
        })

        // Слушатель для изменения EditText (максимум)
        maxValueText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                if (!isProgrammaticChange) {
                    isProgrammaticChange = true
                    val maxValue = s.toString().toIntOrNull() ?: 700
                    val minValue = minValueText.text.toString().toIntOrNull() ?: 5

                    val clampedMax = maxOf(minOf(maxValue, 700), minValue)
                    rangeSeekBar.values = listOf(minValue.toFloat(), clampedMax.toFloat())
                    isProgrammaticChange = false
                }
            }
        })



        return binding.root
    }


    private fun initListeners() {


        binding.buttonBolum.setOnClickListener {
            val bottomSheet = SectionSelectionBottomSheet(emptyList(), onDelete = {})

            bottomSheet.setOnCategorySelectedListener { selectedCity ->
                binding.bolumTextView.text = selectedCity.name

            }

            bottomSheet.show(childFragmentManager, "CustomBottomSheet")
        }

        binding.backBtn.setOnClickListener() {
            findNavController().popBackStack()
        }




        binding.propertiesBtn.setOnClickListener {


        }


        binding.remont.setOnClickListener {

        }

        binding.mumkinchilikler.setOnClickListener {
            OpportunityDialog()
        }

        binding.location.setOnClickListener {
            findNavController().navigate(R.id.action_filterFragment_to_fragmentLocation)
        }

    }


    private fun switchDesign() {

        // Слушатель изменений состояния
        binding.customSwitch.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {

                // Установить цвет для "включённого" состояния
                binding.customSwitch.trackTintList =
                    ContextCompat.getColorStateList(requireContext(), R.color.black)
                binding.customSwitch.trackDecorationTintList =
                    ContextCompat.getColorStateList(requireContext(), R.color.color_transparent)
                binding.customSwitch.thumbTintList =
                    ContextCompat.getColorStateList(requireContext(), R.color.white)
                binding.customSwitch.thumbIconSize = 200

            } else {

                // Установить цвет для "выключенного" состояния
                binding.customSwitch.trackTintList =
                    ContextCompat.getColorStateList(requireContext(), R.color.unchecked_track)
                binding.customSwitch.trackDecorationTintList =
                    ContextCompat.getColorStateList(requireContext(), R.color.color_transparent)
                binding.customSwitch.thumbTintList =
                    ContextCompat.getColorStateList(requireContext(), R.color.white)

            }
        }


        // Слушатель изменений состояния
        binding.customSwitchTwo.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {

                // Установить цвет для "включённого" состояния
                binding.customSwitchTwo.trackTintList =
                    ContextCompat.getColorStateList(requireContext(), R.color.black)
                binding.customSwitchTwo.trackDecorationTintList =
                    ContextCompat.getColorStateList(requireContext(), R.color.color_transparent)
                binding.customSwitchTwo.thumbTintList =
                    ContextCompat.getColorStateList(requireContext(), R.color.white)


            } else {

                // Установить цвет для "выключенного" состояния
                binding.customSwitchTwo.trackTintList =
                    ContextCompat.getColorStateList(requireContext(), R.color.unchecked_track)
                binding.customSwitchTwo.trackDecorationTintList =
                    ContextCompat.getColorStateList(requireContext(), R.color.color_transparent)
                binding.customSwitchTwo.thumbTintList =
                    ContextCompat.getColorStateList(requireContext(), R.color.white)

            }
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
        chip.chipStrokeColor = ContextCompat.getColorStateList(requireContext(), if (isActive) R.color.black else R.color.chip_border_color)
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


    @SuppressLint("MissingInflatedId")
    private fun OpportunityDialog() {
//        TODO: Shu tayyny duzet
        val dialogView = LayoutInflater.from(requireContext())
            .inflate(R.layout.fragment_dialog_mumkinchilikler, null)


        val recyclerViewOpportunities = dialogView.findViewById<RecyclerView>(R.id.recyclerView)

        val opportunityList = listOf(
            OpportunityData(R.drawable.ic_wifi, "Wi-Fi"),
            OpportunityData(R.drawable.ic_bath, "Duş"),
            OpportunityData(R.drawable.ic_kitchen, "Aşhana"),
            OpportunityData(R.drawable.ic_bake, "Peç"),
            OpportunityData(R.drawable.ic_washing_machine, "Kir maşyn"),
            OpportunityData(R.drawable.ic_lift, "Lift"),
            OpportunityData(R.drawable.ic_tv, "Telewizor"),
            OpportunityData(R.drawable.ic_balcony, "Balkon"),
            OpportunityData(R.drawable.ic_air_conditioner, "Kondisioner"),
            OpportunityData(R.drawable.ic_kitchen_furniture, "Aşhana-mebel"),
            OpportunityData(R.drawable.ic_fridge, "Sowadyjy"),
            OpportunityData(R.drawable.ic_swimming_pool, "Basseýn"),
            OpportunityData(R.drawable.ic_bedroom, "Spalny"),
            OpportunityData(R.drawable.ish_stoly_ic, "Iş stoly"),
            OpportunityData(R.drawable.mebel_ic, "Mebel şkaf"),
            OpportunityData(R.drawable.ic_grill, "Mangal"),
            OpportunityData(R.drawable.ic_hot_water, "Gyzgyn suw"),
            OpportunityData(R.drawable.ic_heating_system, "Ýyladyş ylgamy")
        )



// Инициализация адаптера с обработчиком клика
        val opportunityAdapter = OpportunityDialogAdapter(opportunityList, emptyList()) { selectedItem ->
            Toast.makeText(requireContext(), "Выбрано: ${selectedItem.text}", Toast.LENGTH_SHORT).show()
        }

        recyclerViewOpportunities.layoutManager = GridLayoutManager(requireContext(), 2) // Устанавливаем менеджер
        recyclerViewOpportunities.adapter = opportunityAdapter

// Получаем родительский ConstraintLayout


        // Создаём и отображаем диалог
        AlertDialog.Builder(requireContext()).setView(dialogView).setCancelable(true).create()
            .show()
    }



    private fun priceEditText() {

        val minPriceLong = binding.etMinPrice.text.toString().trim().toLongOrNull()
        val maxPriceLong = binding.etMaxPrice.text.toString().trim().toLongOrNull()


        if (minPriceLong != null && maxPriceLong != null && minPriceLong > maxPriceLong) {
            Toast.makeText(
                requireContext(), "Ýalňyş! Min baha maks bahadan uly bolmaly däl!", Toast.LENGTH_SHORT).show()
        }

    }



    private fun showPopupMenu(view: View) {


        val popupMenu = PopupMenu(requireContext(), view, Gravity.END or  Gravity.BOTTOM)

        // Inflate the menu resource
        popupMenu.menuInflater.inflate(R.menu.popup_menu, popupMenu.menu)

        // Set click listener for menu items
        popupMenu.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.option_default -> {
                    Toast.makeText(requireContext(), "Saylanmadyk", Toast.LENGTH_SHORT).show()
                    true
                }
                R.id.option_price_asc -> {
                    Toast.makeText(requireContext(), "Arzandan gymmada", Toast.LENGTH_SHORT).show()
                    true
                }
                R.id.option_price_desc -> {
                    Toast.makeText(requireContext(), "Gymmatdan arzana", Toast.LENGTH_SHORT).show()
                    true
                }
                else -> false
            }
        }

        // Show the popup menu
        popupMenu.show()
    }










}




