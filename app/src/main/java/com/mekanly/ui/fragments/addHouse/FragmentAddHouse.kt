package com.mekanly.ui.fragments.addHouse

import LocationBottomSheet
import android.app.Activity.RESULT_OK
import android.app.AlertDialog
import android.app.Dialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.transition.Visibility
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.mekanly.R
import com.mekanly.data.models.DataGlobalOptions
import com.mekanly.data.request.AddHouseBody
import com.mekanly.databinding.FragmentAddHouseBinding
import com.mekanly.domain.model.ResponseBodyState
import com.mekanly.helpers.PreferencesHelper
import com.mekanly.ui.adapters.AdapterLocalImages
import com.mekanly.ui.adapters.PossibilitySelectedAdapter
import com.mekanly.ui.bottomSheet.SectionSelectionBottomSheet
import com.mekanly.ui.dialog.OptionSelectionDialog
import com.mekanly.ui.dialog.OptionsDialogAdapter.Companion.TYPE_OPPORTUNITY
import com.mekanly.ui.dialog.OptionsDialogAdapter.Companion.TYPE_PROPERTIES
import com.mekanly.ui.dialog.OptionsDialogAdapter.Companion.TYPE_REPAIR
import com.mekanly.ui.login.PhoneNumberTextWatcher
import com.mekanly.utils.Constants.Companion.OWNER
import com.mekanly.utils.Constants.Companion.REALTOR
import com.mekanly.utils.extensions.toInt
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class FragmentAddHouse : Fragment() {

    private lateinit var binding: FragmentAddHouseBinding
    private val addHouseBody = AddHouseBody()
    private lateinit var selectedOptionsAdapter: PossibilitySelectedAdapter
    private val viewModel: VMAddHouse by viewModels()
    private lateinit var hashtagTextWatcher: HashtagTextWatcher
    private var isMenuBuildingVisible = false
    private var isMenuRoomsVisible = false
    private var isMenuFloorVisible = false

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
        phoneNumberTextWatcher()
        hashtagTextWatcher()

        selectedOptionsAdapter = PossibilitySelectedAdapter()
        binding.rvOpportunity.adapter = selectedOptionsAdapter
        binding.rvOpportunity.layoutManager = GridLayoutManager(context, 3)



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

        binding.hashtagInfo.setOnClickListener {

            val dialogView =
                LayoutInflater.from(requireContext()).inflate(R.layout.dialog_hashtag_info, null)



            AlertDialog.Builder(requireContext()).setView(dialogView).setCancelable(true).create()
                .show()


        }

        binding.menuLayout.visibility = View.GONE
        binding.menuLayoutRooms.visibility = View.GONE
        binding.menuLayoutFloor.visibility = View.GONE


        binding.headerLayoutBuilding.setOnClickListener {
            toggleMenu(headerLayout = binding.headerLayoutBuilding,
                dropDownIcon = binding.dropDownBuilding,
                menuLayout = binding.menuLayout,
                isVisible = isMenuBuildingVisible,
                onStateChanged = { isMenuBuildingVisible = it })
        }

        binding.headerLayoutRooms.setOnClickListener {
            toggleMenu(headerLayout = binding.headerLayoutRooms,
                dropDownIcon = binding.dropDownRooms,
                menuLayout = binding.menuLayoutRooms,
                isVisible = isMenuRoomsVisible,
                onStateChanged = { isMenuRoomsVisible = it })
        }

        binding.headerLayoutFloor.setOnClickListener {
            toggleMenu(headerLayout = binding.headerLayoutFloor,
                dropDownIcon = binding.dropDownFloor,
                menuLayout = binding.menuLayoutFloor,
                isVisible = isMenuFloorVisible,
                onStateChanged = { isMenuFloorVisible = it })
        }

        return binding.root
    }


    private fun setImagesAdapter() {
        adapter = AdapterLocalImages { uri -> viewModel.removeImage(uri) }
        binding.rvImages.adapter = adapter
        binding.rvImages.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
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

        binding.toggleGroup.addOnButtonCheckedListener { group, checkedId, isChecked ->
            addHouseBody.who = when {
                binding.btnOwner.isChecked -> OWNER
                binding.btnRealtor.isChecked -> REALTOR
                else -> null.toString()
            }
        }

        binding.layAddImages.setOnClickListener {
            val intent = Intent(Intent.ACTION_OPEN_DOCUMENT).apply {
                addCategory(Intent.CATEGORY_OPENABLE)
                type = "image/*"
                putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
            }
            startActivityForResult(intent, REQUEST_CODE_PICK_IMAGES)
        }

        val progressDialog = Dialog(requireContext()).apply {
            setContentView(R.layout.dialog_loading)
            setCancelable(false)
            window?.setBackgroundDrawableResource(android.R.color.transparent)
        }


        binding.btnDone.setOnClickListener {
            val name = binding.edtName.text.toString().trim()
            val category = binding.txtCategory.text.toString()
            val location = binding.txtLocation.text.toString()
            val isAgreeChecked = binding.switchAgree.isChecked

            fun checkRequiredFields(): Boolean {
                return when {
                    name.isEmpty() -> {
                        Toast.makeText(requireContext(), "Ady giriziň", Toast.LENGTH_SHORT).show()
                        false
                    }

                    category == getString(R.string.not_selected) -> {
                        Toast.makeText(requireContext(), "Bölümi saýlaň", Toast.LENGTH_SHORT).show()
                        false
                    }

                    location == getString(R.string.not_selected) -> {
                        Toast.makeText(requireContext(), "Ýerini saýlaň", Toast.LENGTH_SHORT).show()
                        false
                    }

                    !isAgreeChecked -> {
                        Toast.makeText(requireContext(), "Ylalaşyga razy boluň", Toast.LENGTH_SHORT)
                            .show()
                        false
                    }

                    else -> true
                }
            }

            if (checkRequiredFields()) {
                showConfirmationDialog {
                    addHouseBody.apply {
                        this@apply.name = binding.edtName.text?.trim().toString()
                        description = binding.edtDescription.text?.trim().toString()
                        area = binding.editTextArea.text?.trim().toString().toIntOrNull()
                        price = binding.editTextPrice.text?.trim().toString().toIntOrNull()
                        hashtag = binding.editTextHashTag.text?.trim().toString()
                        roomNumber = binding.chipGroupRooms.getSelectedChipInt()
                        floorNumber = binding.chipGroupFloor.getSelectedChipInt()
                        levelNumber = binding.chipGroupLevel.getSelectedChipInt()
                    }

                    progressDialog.show()

                    lifecycleScope.launch(Dispatchers.IO) {
                        viewModel.addHouse(
                            addHouseBody, viewModel.convertUrisToFiles(
                                requireContext(), viewModel.images.value
                            )
                        ) {
                            when (it) {
                                is ResponseBodyState.Error -> {
                                    lifecycleScope.launch(Dispatchers.Main) {
                                        progressDialog.dismiss()
                                        Toast.makeText(
                                            requireContext(),
                                            "Ýalňyşlyk ýüze çykdy",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                    }
                                }
                                ResponseBodyState.Loading -> {}


                                is ResponseBodyState.Success -> {
                                    Log.d("ADD_HOUSE", "Üstünlikli ugradyldy")
                                    lifecycleScope.launch(Dispatchers.Main) {
                                        progressDialog.dismiss()
                                        showSuccessDialog()
                                    }
                                }
                                else -> {
                                    lifecycleScope.launch(Dispatchers.Main) {
                                        progressDialog.dismiss()
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }


    }

    private fun openCategorySelector(globalOptions: DataGlobalOptions) {
        val houseCategories = globalOptions.houseCategories
        val bottomSheet = SectionSelectionBottomSheet(houseCategories, onDelete = {
            addHouseBody.categoryId = null
            binding.txtCategory.text = getString(R.string.not_selected)
        })

        bottomSheet.setOnCategorySelectedListener { category ->
            addHouseBody.categoryId = category.id
            binding.txtCategory.text = category.name
        }

        bottomSheet.show(childFragmentManager, "CustomBottomSheet")
    }

    private fun openLocationSelector(globalOptions: DataGlobalOptions) {
        val parentCities = globalOptions.locations.filter { it.parentId == null }

        if (parentCities.isNotEmpty()) {
            LocationBottomSheet.showWithChildren(
                parent = this,
                cities = parentCities,
                onDelete = {
                    addHouseBody.locationId = null
                    binding.txtLocation.text = getString(R.string.not_selected)
                },
                onCitySelected = { selectedCity ->
                    addHouseBody.locationId = selectedCity.id
                    binding.txtLocation.text = selectedCity.name
                }
            )
        }
    }




    private fun showPropertyTypeDialog(globalOptions: DataGlobalOptions) {
        val dialog = OptionSelectionDialog(requireContext(),
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
        val dialog = OptionSelectionDialog(requireContext(),
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
        val dialog = OptionSelectionDialog(requireContext(),
            TYPE_OPPORTUNITY,
            title = R.string.possibilities,
            singleSelection = false,
            items = globalOptions.possibility,
            onConfirm = { res ->
                if (res.isNotEmpty()) {
                    binding.txtPossibilities.text = "Saýlanan"
                    binding.possibilitiesDown.visibility = View.GONE

                    // ✅ Обновляем список в RecyclerView
                    selectedOptionsAdapter.setOptions(res)

                    // ✅ Добавь эту строку:
                    addHouseBody.possibilities = res.map { it.id }

                } else {
                    binding.txtPossibilities.text = getString(R.string.not_selected)
                    addHouseBody.possibilities = null

                    // Очищаем список в RecyclerView
                    selectedOptionsAdapter.setOptions(emptyList())
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


    private fun toggleMenu(
        headerLayout: View,
        dropDownIcon: ImageView,
        menuLayout: View,
        isVisible: Boolean,
        onStateChanged: (Boolean) -> Unit
    ) {
        if (isVisible) {
            // Скрываем меню с анимацией
            menuLayout.animate().alpha(0.0f).setDuration(400).withEndAction {
                menuLayout.visibility = View.GONE
            }

            // Поворачиваем иконку вниз (исходное положение)
            dropDownIcon.animate().rotation(0f).setDuration(300).start()

            onStateChanged(false)
        } else {
            // Показываем меню с анимацией
            menuLayout.alpha = 0.0f
            menuLayout.visibility = View.VISIBLE
            menuLayout.animate().alpha(1.0f).setDuration(400)

            // Поворачиваем иконку вверх
            dropDownIcon.animate().rotation(-180f).setDuration(300).start()

            onStateChanged(true)
        }
    }


    private fun phoneNumberTextWatcher() {

        binding.editTextPhone.text.toString().replace(" ", "")

        val phoneEditText = binding.editTextPhone
        val watcher = PhoneNumberTextWatcher(phoneEditText,
            object : PhoneNumberTextWatcher.PhoneNumberValidationCallback {
                override fun onPhoneNumberValid() {
                    Log.d("PhoneValidation", "Номер валиден")
                }

                override fun onPhoneNumberInvalid() {
                    Log.d("PhoneValidation", "Номер невалиден")
                }
            })

        phoneEditText.addTextChangedListener(watcher)

    }

    private fun hashtagTextWatcher() {

        // Создаем экземпляр HashtagTextWatcher и применяем его к EditText
        hashtagTextWatcher = HashtagTextWatcher(binding.editTextHashTag)
        binding.editTextHashTag.addTextChangedListener(hashtagTextWatcher)

        // Если вы хотите, чтобы EditText изначально имел хэштег при открытии фрагмента
        if (binding.editTextHashTag.text?.isEmpty() == true) {
            binding.editTextHashTag.setText("#")
            binding.editTextHashTag.text?.let { binding.editTextHashTag.setSelection(it.length) }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding.editTextHashTag.removeTextChangedListener(hashtagTextWatcher)
    }


    private fun showConfirmationDialog(onConfirm: () -> Unit) {
        val dialog = Dialog(requireContext())
        dialog.setContentView(R.layout.dialog_confirmation)
        dialog.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)

        dialog.findViewById<Button>(R.id.btn_positive).setOnClickListener {
            dialog.dismiss()
            onConfirm()
        }

        dialog.findViewById<Button>(R.id.btn_negative).setOnClickListener {
            dialog.dismiss()
        }

        dialog.show()
    }

    private fun showSuccessDialog() {
        val dialog = Dialog(requireContext())
        dialog.setContentView(R.layout.dialog_success)
        dialog.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        dialog.setCancelable(false)

        dialog.findViewById<Button>(R.id.btn_ok).setOnClickListener {
            dialog.dismiss()
            findNavController().popBackStack()
        }

        dialog.show()
    }


}




fun ChipGroup.getSelectedChipInt(): Int? {
    return findViewById<Chip>(checkedChipId)?.text?.toString()?.toIntOrNull()
}







