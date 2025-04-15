package com.mekanly.ui.fragments.addHouse

import LocationBottomSheet
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.mekanly.R
import com.mekanly.data.models.DataGlobalOptions
import com.mekanly.data.request.AddHouseBody
import com.mekanly.databinding.FragmentAddHouseBinding
import com.mekanly.domain.model.ResponseBodyState
import com.mekanly.helpers.PreferencesHelper
import com.mekanly.ui.bottomSheet.SectionSelectionBottomSheet
import com.mekanly.ui.dialog.OptionSelectionDialog
import com.mekanly.ui.dialog.OptionsDialogAdapter.Companion.TYPE_OPPORTUNITY
import com.mekanly.ui.dialog.OptionsDialogAdapter.Companion.TYPE_PROPERTIES
import com.mekanly.ui.dialog.OptionsDialogAdapter.Companion.TYPE_REPAIR
import com.mekanly.utils.extensions.toInt
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class FragmentAddHouse : Fragment() {

    private lateinit var binding: FragmentAddHouseBinding
    private val addHouseBody = AddHouseBody()
    private val viewModel: VMAddHouse by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentAddHouseBinding.inflate(inflater, container, false)
        val globalOptions = PreferencesHelper.getGlobalOptions()
        switchDesign()
        binding.backBtn.setOnClickListener {
            findNavController().popBackStack()
        }
        globalOptions?.let { initListeners(it) }
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

        binding.btnDone.setOnClickListener {

            addHouseBody.apply {
                name = binding.edtName.text?.trim().toString()
                description = binding.edtDescription.text?.trim().toString()
                area = binding.editTextArea.text?.trim().toString().toIntOrNull()
                price = binding.editTextPrice.text?.trim().toString().toIntOrNull()
                hashtag = binding.editTextHashTag.text?.trim().toString()

                roomNumber = binding.chipGroupRooms.getSelectedChipInt()
                floorNumber = binding.chipGroupFloor.getSelectedChipInt()
                levelNumber = binding.chipGroupLevel.getSelectedChipInt()
            }

            lifecycleScope.launch(Dispatchers.IO) {
                viewModel.addHouse(addHouseBody) {
                    when (it) {
                        is ResponseBodyState.Error -> {
                            Log.e("ADD_HOUSE", "initListeners: " + it.error)
                        }

                        ResponseBodyState.Loading -> {
                            Log.e("ADD_HOUSE", "initListeners: loading")
                        }

                        is ResponseBodyState.Success -> {
                            Log.e("ADD_HOUSE", "Success")
                        }

                        else -> {}
                    }
                }
            }

        }

    }

    private fun openCategorySelector(globalOptions: DataGlobalOptions) {
        val houseCategories = globalOptions.houseCategories
        val bottomSheet = SectionSelectionBottomSheet(
            houseCategories, onDelete = {
                addHouseBody.categoryId = null
                binding.txtCategory.text = getString(R.string.not_selected)
            }
        )

        bottomSheet.setOnCategorySelectedListener { category ->
            addHouseBody.categoryId = category.id
            binding.txtCategory.text = category.name
        }

        bottomSheet.show(childFragmentManager, "CustomBottomSheet")
    }

    private fun openLocationSelector(globalOptions: DataGlobalOptions) {
        if (globalOptions.locations.isNotEmpty()) {
            val cities = globalOptions.locations
            val bottomSheet = LocationBottomSheet(
                cities, onDelete = {
                    addHouseBody.locationId = null
                    binding.txtLocation.text = getString(R.string.not_selected)
                }
            ) { selectedCity ->
                addHouseBody.locationId = selectedCity.id
                binding.txtLocation.text = selectedCity.name

            }
            bottomSheet.show(childFragmentManager, "LocationBottomSheet")
        }
    }


    private fun showPropertyTypeDialog(globalOptions: DataGlobalOptions) {
        val dialog = OptionSelectionDialog(
            requireContext(),
            TYPE_PROPERTIES,
            title = R.string.property_type,
            singleSelection = true,
            items = globalOptions.propertyType,
            onConfirm = { res ->
                if (res.isNotEmpty()) {
                    binding.txtPropertyType.text = res[0].name
                    addHouseBody.propertyTypeId = res[0].id
                } else {
                    binding.txtPropertyType.text = getString(R.string.not_selected)
                    addHouseBody.propertyTypeId = null
                }
            })
        dialog.show()
    }

    private fun showRepairTypeDialog(globalOptions: DataGlobalOptions) {
        val dialog = OptionSelectionDialog(
            requireContext(),
            TYPE_REPAIR,
            title = R.string.repair,
            singleSelection = true,
            items = globalOptions.repairType,
            onConfirm = { res ->
                if (res.isNotEmpty()) {
                    binding.txtRepairType.text = res[0].name
                    addHouseBody.repairTypeId = res[0].id
                } else {
                    binding.txtRepairType.text = getString(R.string.not_selected)
                    addHouseBody.repairTypeId = null
                }
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
            onConfirm = { res ->
                if (res.isNotEmpty()) {
                    binding.txtPossibilities.text = res.joinToString(", ") { it.name }
                    addHouseBody.possibilities = res.map { it.id }
                } else {
                    binding.txtPossibilities.text = getString(R.string.not_selected)
                    addHouseBody.possibilities = null
                }
            })
        dialog.show()
    }

    private fun switchDesign() {

        addHouseBody.writeComment = binding.switchCanComment.isChecked.toInt()
        addHouseBody.exclusive = binding.switchOnlyInMekanly.isChecked.toInt()

        binding.switchCanComment.setOnCheckedChangeListener { _, isChecked ->
            addHouseBody.writeComment = isChecked.toInt()
            val trackTint = if (isChecked) R.color.black else R.color.unchecked_track
            val thumbTint = R.color.white
            val decorationTint = R.color.color_transparent

            binding.apply {
                switchCanComment.trackTintList =
                    ContextCompat.getColorStateList(requireContext(), trackTint)
                switchCanComment.trackDecorationTintList =
                    ContextCompat.getColorStateList(requireContext(), decorationTint)
                switchCanComment.thumbTintList =
                    ContextCompat.getColorStateList(requireContext(), thumbTint)

                if (isChecked) {
                    switchCanComment.thumbIconSize = 100
                }
            }
        }


        binding.switchAgree.setOnCheckedChangeListener { _, isChecked ->
            val trackTint = if (isChecked) R.color.black else R.color.unchecked_track
            val thumbTint = R.color.white
            val decorationTint = R.color.color_transparent

            binding.apply {
                switchAgree.trackTintList =
                    ContextCompat.getColorStateList(requireContext(), trackTint)
                switchAgree.trackDecorationTintList =
                    ContextCompat.getColorStateList(requireContext(), decorationTint)
                switchAgree.thumbTintList =
                    ContextCompat.getColorStateList(requireContext(), thumbTint)

                if (isChecked) {
                    switchAgree.thumbIconSize = 100
                }
            }
        }


        binding.switchOnlyInMekanly.setOnCheckedChangeListener { _, isChecked ->
            addHouseBody.exclusive = isChecked.toInt()
            val trackTint = if (isChecked) R.color.black else R.color.unchecked_track
            val thumbTint = R.color.white
            val decorationTint = R.color.color_transparent

            binding.apply {
                switchOnlyInMekanly.trackTintList =
                    ContextCompat.getColorStateList(requireContext(), trackTint)
                switchOnlyInMekanly.trackDecorationTintList =
                    ContextCompat.getColorStateList(requireContext(), decorationTint)
                switchOnlyInMekanly.thumbTintList =
                    ContextCompat.getColorStateList(requireContext(), thumbTint)

                if (isChecked) {
                    switchOnlyInMekanly.thumbIconSize = 100
                }
            }
        }
    }
}

fun ChipGroup.getSelectedChipInt(): Int? {
    return findViewById<Chip>(checkedChipId)?.text?.toString()?.toIntOrNull()
}







