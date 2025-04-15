package com.mekanly.ui.fragments.addHouse

import LocationBottomSheet
import android.app.Activity.RESULT_OK
import android.content.Intent
import android.net.Uri
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
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.mekanly.R
import com.mekanly.data.models.DataGlobalOptions
import com.mekanly.data.request.AddHouseBody
import com.mekanly.databinding.FragmentAddHouseBinding
import com.mekanly.domain.model.ResponseBodyState
import com.mekanly.helpers.PreferencesHelper
import com.mekanly.ui.adapters.AdapterLocalImages
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

    companion object {
        const val REQUEST_CODE_PICK_IMAGES = 1001
    }
    private lateinit var adapter: AdapterLocalImages

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
        setImagesAdapter()
        lifecycleScope.launchWhenStarted {
            viewModel.images.collect { imageUris ->
                adapter.submitList(imageUris)
                binding.txtImageCount.text = "${imageUris.size}/15"
            }

        }

        return binding.root
    }

    private fun setImagesAdapter(){
        adapter = AdapterLocalImages { uri -> viewModel.removeImage(uri) }
        binding.rvImages.adapter = adapter
        binding.rvImages.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
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

        binding.radioGroupPoster.setOnCheckedChangeListener { group, checkedId ->
            addHouseBody.who = when (checkedId) {
                R.id.radioBtnOwner -> "Eýesinden"
                R.id.radioBtnRealtor -> "realtordan"
                else -> "Eýesinden"
            }
        }

        binding.layAddImages.setOnClickListener{
            val intent = Intent(Intent.ACTION_OPEN_DOCUMENT).apply {
                addCategory(Intent.CATEGORY_OPENABLE)
                type = "image/*"
                putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
            }
            startActivityForResult(intent, REQUEST_CODE_PICK_IMAGES)
        }
        binding.btnDone.setOnClickListener {

            //TODO
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
                viewModel.addHouse(addHouseBody, viewModel.convertUrisToFiles(requireContext(), viewModel.images.value) ) {
                    when (it) {
                        is ResponseBodyState.Error -> {
                            Log.e("ADD_HOUSE", "initListeners: " + it.error)
                        }

                        ResponseBodyState.Loading -> {
                            Log.e("ADD_HOUSE", "initListeners: loading")
                        }

                        is ResponseBodyState.Success -> {
                            Log.e("ADD_HOUSE", "Success")
                            findNavController().popBackStack()
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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == REQUEST_CODE_PICK_IMAGES && resultCode == RESULT_OK) {
            val uris = mutableListOf<Uri>()
            data?.let {
                if (it.clipData != null) {
                    for (i in 0 until it.clipData!!.itemCount) {
                        uris.add(it.clipData!!.getItemAt(i).uri)
                    }
                } else if (it.data != null) {
                    uris.add(it.data!!)
                }
            }
            viewModel.addImages(uris)
        }
    }

}

fun ChipGroup.getSelectedChipInt(): Int? {
    return findViewById<Chip>(checkedChipId)?.text?.toString()?.toIntOrNull()
}







