package com.mekanly.ui.fragments.filter

import LocationBottomSheet
import android.graphics.Typeface
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
import androidx.activity.addCallback
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.google.android.material.slider.RangeSlider
import com.mekanly.R
import com.mekanly.data.models.DataGlobalOptions
import com.mekanly.data.models.PriceRange
import com.mekanly.databinding.FragmentFilterBinding
import com.mekanly.helpers.PreferencesHelper
import com.mekanly.ui.bottomSheet.SectionSelectionBottomSheet
import com.mekanly.ui.dialog.OptionSelectionDialog
import com.mekanly.ui.dialog.OptionsDialogAdapter.Companion.TYPE_OPPORTUNITY
import com.mekanly.ui.dialog.OptionsDialogAdapter.Companion.TYPE_PROPERTIES
import com.mekanly.ui.dialog.OptionsDialogAdapter.Companion.TYPE_REPAIR
import com.mekanly.ui.fragments.search.viewModel.VMSearch
import com.mekanly.utils.Constants.Companion.OWNER
import com.mekanly.utils.Constants.Companion.REALTOR
import com.mekanly.utils.Constants.Companion.SORT_BY_CREATED_AT
import com.mekanly.utils.Constants.Companion.SORT_BY_PRICE
import com.mekanly.utils.Constants.Companion.SORT_ORDER_ASC
import com.mekanly.utils.Constants.Companion.SORT_ORDER_DESC
import kotlinx.coroutines.launch


class FilterFragment : Fragment() {
    private lateinit var binding: FragmentFilterBinding

    private lateinit var rangeSlider: RangeSlider
    private lateinit var minValueTextView: TextView
    private lateinit var maxValueTextView: TextView

    private val viewModel: VMSearch by activityViewModels()
    private var isProgrammaticChange = false


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentFilterBinding.inflate(inflater, container, false)

        val globalOptions = PreferencesHelper.getGlobalOptions()

        globalOptions?.let { initListeners(it) }
        chipGroups()
        switchDesign()
        seekBarLogicTerritory()
        priceEditText()
        observeViewModel()

        binding.backBtn.setOnClickListener {
            viewModel.clearAllFilters()
            findNavController().popBackStack()
        }

        binding.searchButton.setOnClickListener {
            viewModel.getHouses()
            findNavController().popBackStack()
        }

        return binding.root
    }


    private fun initListeners(globalOptions: DataGlobalOptions) {

        binding.propertiesBtn.setOnClickListener {
            showPropertyTypeDialog(globalOptions)
        }

        binding.remont.setOnClickListener {
            showRepairTypeDialog(globalOptions)
        }

        binding.mumkinchilikler.setOnClickListener {
            showPossibilityTypeDialog(globalOptions)
        }

        binding.location.setOnClickListener {
            openLocationSelector(globalOptions)
        }

        binding.category.setOnClickListener {
            openCategorySelector(globalOptions)
        }



        binding.popupMenu.setOnClickListener { view ->
            showPopupMenu(view)
        }
    }

    private fun observeViewModel() {

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            viewModel.clearAllFilters()
            findNavController().popBackStack()
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.selectedLocation.collect { location ->
                        binding.txtLocation.text =
                            location?.name ?: getString(R.string.not_selected)
                    }
                }
                launch {
                    viewModel.selectedCategory.collect { category ->
                        binding.txtCategory.text =
                            category?.name ?: getString(R.string.not_selected)
                    }
                }
                launch {
                    viewModel.price.collect { price ->
                        binding.etMaxPrice.setText((price.max ?: 0).toString())
                        binding.etMinPrice.setText((price.min ?: 0).toString())
                    }
                }
                launch {
                    viewModel.floorNumber.collect { floorNumber ->
                        for (i in 0 until binding.chipGroupFloor.childCount) {
                            val chip = binding.chipGroupFloor.getChildAt(i) as Chip
                            val chipValue = chip.text.toString().toIntOrNull()
                            if (chip.id == R.id.allChipsTwo) {
                                val shouldActivateAll = viewModel.floorNumber.value.isEmpty()
                                activateChip(chip, shouldActivateAll)
                            } else {
                                val shouldBeSelected =
                                    chipValue != null && viewModel.floorNumber.value.contains(chipValue)
                                activateChip(chip, shouldBeSelected)
                            }
                        }
                    }
                }
                launch {
                    viewModel.roomNumber.collect { rooms ->
                        for (i in 0 until binding.chipGroupRooms.childCount) {
                            val chip = binding.chipGroupRooms.getChildAt(i) as Chip
                            val chipValue = chip.text.toString().toIntOrNull()
                            if (chip.id == R.id.allChips) {
                                val shouldActivateAll = viewModel.roomNumber.value.isEmpty()
                                activateChip(chip, shouldActivateAll)
                            } else {
                                val shouldBeSelected =
                                    chipValue != null && viewModel.roomNumber.value.contains(chipValue)
                                activateChip(chip, shouldBeSelected)
                            }
                        }
                    }
                }
                launch {
                    viewModel.selectedPropertyTypes.collect { option ->
                        binding.txtPropertyType.text = if (option.isEmpty()) {
                            getString(R.string.not_selected)
                        } else {
                            option.joinToString(", ") { it.name }
                        }
                    }
                }

                launch {
                    viewModel.selectedRepairTypes.collect { option ->
                        binding.txtRepairType.text = if (option.isEmpty()) {
                            getString(R.string.not_selected)
                        } else {
                            option.joinToString(", ") { it.name }
                        }
                    }
                }

                launch {
                    viewModel.selectedOpportunities.collect { option ->
                        binding.txtOpportunities.text = if (option.isEmpty()) {
                            getString(R.string.not_selected)
                        } else {
                            option.joinToString(", ") { it.name }
                        }
                    }
                }
                launch {
                    viewModel.area.collect { area ->
                        val minValue = area.min ?: binding.rangeSeekBar.valueFrom.toInt()
                        val maxValue = area.max ?: binding.rangeSeekBar.valueTo.toInt()

                        val minValueText = "$minValue m²"
                        val maxValueText = if (maxValue >= rangeSlider.valueTo.toInt()) {
                            "$maxValue+ m²"
                        } else {
                            "$maxValue m²"
                        }
                        minValueTextView.text = minValueText
                        maxValueTextView.text = maxValueText
                    }
                }
                launch {
                    viewModel.sortBy.collect { sortBy ->
                        if (sortBy != SORT_BY_PRICE) {
                            binding.txtSortBy.text = getString(R.string.not_selected)
                        }
                    }
                }

                launch {
                    viewModel.sortOrder.collect { sortBy ->
                        val isSortingByPrice = viewModel.sortBy.value == SORT_BY_PRICE

                        binding.txtSortBy.text = when {
                            isSortingByPrice && sortBy == SORT_ORDER_ASC -> getString(R.string.price_from_low_to_high)
                            isSortingByPrice && sortBy == SORT_ORDER_DESC -> getString(R.string.price_from_high_to_low)
                            else -> getString(R.string.not_selected)
                        }
                    }
                }

                when (viewModel.owner.value) {
                    OWNER -> binding.radioGroupPoster.check(R.id.radioBtnOwner)
                    REALTOR -> binding.radioGroupPoster.check(R.id.radioBtnRealtor)
                }


                binding.switchShowOnlyImages.isChecked = viewModel.image.value


            }
        }
    }


    private fun openCategorySelector(globalOptions: DataGlobalOptions) {
        val houseCategories = globalOptions.houseCategories
        val bottomSheet = SectionSelectionBottomSheet(
            houseCategories, onDelete = {
                viewModel.setSelectedCategory(null)
                binding.txtCategory.text = getString(R.string.category)
            }
        )

        bottomSheet.setOnCategorySelectedListener { category ->
            viewModel.setSelectedCategory(category)
        }

        bottomSheet.show(childFragmentManager, "CustomBottomSheet")
    }

    private fun openLocationSelector(globalOptions: DataGlobalOptions) {
        if (globalOptions.locations.isNotEmpty()) {
            val cities = globalOptions.locations
            val bottomSheet = LocationBottomSheet(
                cities, onDelete = {
                    viewModel.setSelectedLocation(null)
                    binding.txtLocation.text = getString(R.string.location)
                }
            ) { selectedCity ->
                viewModel.setSelectedLocation(selectedCity)
            }
            bottomSheet.show(childFragmentManager, "LocationBottomSheet")
        }
    }


    private fun showPropertyTypeDialog(globalOptions: DataGlobalOptions) {
        val dialog = OptionSelectionDialog(
            requireContext(),
            TYPE_PROPERTIES,
            title = R.string.property_type,
            singleSelection = false,
            items = globalOptions.propertyType,
            selectedItems = viewModel.selectedPropertyTypes.value,
            onConfirm = { res ->
                viewModel.setSelectedPropertyTypes(res)
            })
        dialog.show()
    }

    private fun showRepairTypeDialog(globalOptions: DataGlobalOptions) {
        val dialog = OptionSelectionDialog(
            requireContext(),
            TYPE_REPAIR,
            title = R.string.repair,
            singleSelection = false,
            items = globalOptions.repairType,
            selectedItems = viewModel.selectedRepairTypes.value,
            onConfirm = { res ->
                viewModel.setSelectedRepairTypes(res)
            })
        dialog.show()
    }

    private fun showPossibilityTypeDialog(globalOptions: DataGlobalOptions) {
        val dialog = OptionSelectionDialog(
            requireContext(),
            TYPE_OPPORTUNITY,
            title = R.string.possibilities,
            singleSelection = false,
            items = globalOptions.possibility,
            selectedItems = viewModel.selectedOpportunities.value,
            onConfirm = { res ->
                viewModel.setSelectedOpportunities(res)
            })
        dialog.show()
    }


    private fun switchDesign() {
        binding.switchShowOnlyImages.setOnCheckedChangeListener { _, isChecked ->
            viewModel.setImage(isChecked)
            if (isChecked) {
                binding.switchShowOnlyImages.trackTintList =
                    ContextCompat.getColorStateList(requireContext(), R.color.chip_filter_text_color)
                binding.switchShowOnlyImages.trackDecorationTintList =
                    ContextCompat.getColorStateList(requireContext(), R.color.color_transparent)
                binding.switchShowOnlyImages.thumbTintList =
                    ContextCompat.getColorStateList(requireContext(), R.color.white)
                binding.switchShowOnlyImages.thumbIconSize = 200
            } else {
                binding.switchShowOnlyImages.trackTintList =
                    ContextCompat.getColorStateList(requireContext(), R.color.unchecked_track)
                binding.switchShowOnlyImages.trackDecorationTintList =
                    ContextCompat.getColorStateList(requireContext(), R.color.color_transparent)
                binding.switchShowOnlyImages.thumbTintList =
                    ContextCompat.getColorStateList(requireContext(), R.color.white)
            }
        }


        // Слушатель изменений состояния
        binding.switchShowDateDesc.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                binding.switchShowDateDesc.trackTintList =
                    ContextCompat.getColorStateList(requireContext(), R.color.chip_filter_text_color)
                binding.switchShowDateDesc.trackDecorationTintList =
                    ContextCompat.getColorStateList(requireContext(), R.color.color_transparent)
                binding.switchShowDateDesc.thumbTintList =
                    ContextCompat.getColorStateList(requireContext(), R.color.white)


            } else {
                binding.switchShowDateDesc.trackTintList =
                    ContextCompat.getColorStateList(requireContext(), R.color.unchecked_track)
                binding.switchShowDateDesc.trackDecorationTintList =
                    ContextCompat.getColorStateList(requireContext(), R.color.color_transparent)
                binding.switchShowDateDesc.thumbTintList =
                    ContextCompat.getColorStateList(requireContext(), R.color.white)

            }
        }
    }

    private fun chipGroups() {
        setupChipGroup(binding.chipGroupRooms, R.id.allChips) { num ->
            viewModel.setRoomNumber(num)
        }
        setupChipGroup(binding.chipGroupFloor, R.id.allChipsTwo) { num ->
            viewModel.setFloorNumber(num)
        }
    }

    private fun setupChipGroup(
        chipGroup: ChipGroup,
        allChipsId: Int,
        onChipSelected: (List<Int>) -> Unit
    ) {
        val allChip = chipGroup.findViewById<Chip>(allChipsId)

        // Активируем "Все" по умолчанию
        activateChip(allChip, true)

        for (i in 0 until chipGroup.childCount) {
            val chip = chipGroup.getChildAt(i) as Chip

            chip.setOnClickListener {
                if (chip.id == allChipsId) {
                    if (!chip.isSelected) {
                        // Активируем "Все", деактивируем остальных
                        activateChip(allChip, true)
                        for (j in 0 until chipGroup.childCount) {
                            val otherChip = chipGroup.getChildAt(j) as Chip
                            if (otherChip.id != allChipsId) {
                                activateChip(otherChip, false)
                            }
                        }
                        onChipSelected(emptyList())
                    }
                } else {
                    // Переключаем выбранность
                    chip.isSelected = !chip.isSelected
                    activateChip(chip, chip.isSelected)

                    // Отключаем "Все"
                    if (chip.isSelected) {
                        activateChip(allChip, false)
                    }

                    // Собираем выбранные чипы
                    val selectedIds = mutableListOf<Int>()
                    for (j in 0 until chipGroup.childCount) {
                        val otherChip = chipGroup.getChildAt(j) as Chip
                        if (otherChip.id != allChipsId && otherChip.isSelected) {
                            otherChip.text.toString().toIntOrNull()?.let { selectedIds.add(it) }
                        }
                    }

                    if (selectedIds.isEmpty()) {
                        activateChip(allChip, true)
                        onChipSelected(emptyList())
                    } else {
                        onChipSelected(selectedIds)
                    }
                }
            }
        }
    }

    private fun activateChip(chip: Chip, isActive: Boolean) {
        chip.isSelected = isActive

        // Цвета фона и текста
        chip.chipBackgroundColor = ContextCompat.getColorStateList(
            requireContext(), if (isActive) R.color.chip_color_bg else R.color.white
        )
        chip.setTextColor(
            ContextCompat.getColor(requireContext(), if (isActive) R.color.chip_filter_text_color else R.color.black)
        )

        // Прямая установка DEFAULT или DEFAULT_BOLD
        chip.typeface = if (isActive) Typeface.DEFAULT_BOLD else Typeface.DEFAULT

        chip.chipStrokeColor = ContextCompat.getColorStateList(requireContext(),
            if (isActive) R.color.color_transparent else R.color.chip_border)
    }


    private fun seekBarLogicTerritory() {

        with(binding) {
            rangeSlider = rangeSeekBar
            minValueTextView = minValueText
            maxValueTextView = maxValueText

            val area = viewModel.area.value
            area.min?.let { rangeSeekBar.valueFrom = it.toFloat() }
            area.max?.let { rangeSeekBar.valueTo = it.toFloat() }

            val initialMin = rangeSeekBar.valueFrom
            val initialMax = rangeSeekBar.valueTo

            rangeSeekBar.values = listOf(initialMin, initialMax)
            updateTextViewsInSeekBar(initialMin, initialMax)

            rangeSeekBar.addOnChangeListener { slider, _, fromUser ->
                if (fromUser && !isProgrammaticChange) {
                    isProgrammaticChange = true
                    val (currentMin, currentMax) = slider.values
                    updateTextViewsInSeekBar(currentMin, currentMax)
                    isProgrammaticChange = false
                }

            }
        }


        val rangeSeekBar = binding.rangeSeekBar
        val minValueText = binding.minValueText
        val maxValueText = binding.maxValueText
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


    }

    private fun updateTextViewsInSeekBar(minValue: Float, maxValue: Float) {
        viewModel.setArea(PriceRange(minValue.toInt(), maxValue.toInt()))
    }

    private fun priceEditText() {

        val minPriceLong = binding.etMinPrice.text.toString().trim().toLongOrNull()
        val maxPriceLong = binding.etMaxPrice.text.toString().trim().toLongOrNull()


        if (minPriceLong != null && maxPriceLong != null && minPriceLong > maxPriceLong) {
            Toast.makeText(
                requireContext(),
                "Ýalňyş! Min baha maks bahadan uly bolmaly däl!",
                Toast.LENGTH_SHORT
            ).show()
        }
    }


    private fun showPopupMenu(view: View) {
        val popupMenu = PopupMenu(requireContext(), view, Gravity.END or Gravity.BOTTOM)
        popupMenu.menuInflater.inflate(R.menu.popup_menu, popupMenu.menu)
        popupMenu.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.option_default -> {
                    viewModel.setSortBy(SORT_BY_CREATED_AT)
                    viewModel.setSortOrder(SORT_ORDER_DESC)
                    true
                }

                R.id.option_price_asc -> {
                    viewModel.setSortBy(SORT_BY_PRICE)
                    viewModel.setSortOrder(SORT_ORDER_ASC)
                    true
                }

                R.id.option_price_desc -> {
                    viewModel.setSortBy(SORT_BY_PRICE)
                    viewModel.setSortOrder(SORT_ORDER_DESC)
                    true
                }

                else -> false
            }
        }

        popupMenu.show()
    }


}




