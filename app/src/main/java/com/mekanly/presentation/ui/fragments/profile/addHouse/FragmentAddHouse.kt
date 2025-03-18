package com.mekanly.presentation.ui.fragments.profile.addHouse

import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mekanly.R
import com.mekanly.data.PropertiesDialogData
import com.mekanly.databinding.FragmentAddHouseBinding
import com.mekanly.presentation.ui.adapters.AdapterImageDownload
import com.mekanly.presentation.ui.bottomSheet.SectionSelectionBottomSheet
import com.mekanly.presentation.ui.dialog.propertiesDialog.PropertiesDialogAdapter

class FragmentAddHouse : Fragment() {

    private val images = mutableListOf<Bitmap>()
    private lateinit var imageAdapter: AdapterImageDownload
    private lateinit var binding: FragmentAddHouseBinding

    @SuppressLint("NotifyDataSetChanged")
    private val imagePickerLauncher =
        registerForActivityResult(ActivityResultContracts.GetMultipleContents()) { uris ->
            if (images.size + uris.size > 15) {
                Toast.makeText(requireContext(), "Максимум 15 картинок", Toast.LENGTH_SHORT).show()
            } else {
                uris.forEach { uri ->
                    val bitmap = MediaStore.Images.Media.getBitmap(requireActivity().contentResolver, uri)
                    images.add(bitmap)
                }
                imageAdapter.notifyDataSetChanged()
                updateCounter()
            }
        }





    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentAddHouseBinding.inflate(inflater, container, false)
        switchDesign()

        initListeners()


        binding.propertiesBtn.setOnClickListener { showCustomDialog() }
        binding.remont.setOnClickListener { RemontDialog() }
//        binding.mumkinchilikler.setOnClickListener { OpportunityDialog() }
        binding.back.setOnClickListener { findNavController().popBackStack() }

        binding.imageRecyclerView.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        imageAdapter = AdapterImageDownload(images, 0)
        binding.imageRecyclerView.adapter = imageAdapter

        binding.addImageButton.setOnClickListener {
            if (images.size < 15) {
                imagePickerLauncher.launch("image/*")
            } else {
                Toast.makeText(requireContext(), "Максимум 15 картинок", Toast.LENGTH_SHORT).show()
            }
        }

        return binding.root
    }

    private fun initListeners() {

//        binding.buttonBolum.setOnClickListener {
//            val bottomSheet = SectionSelectionBottomSheet()
//
//            bottomSheet.setOnCitySelectedListener { selectedCity ->
//                binding.bolumTextView.text = selectedCity
//
//            }
//
//            bottomSheet.show(childFragmentManager, "CustomBottomSheet")
//        }

    }



    @SuppressLint("SetTextI18n")
    private fun updateCounter() {
        binding.imageCounter.text = "${images.size}/15"
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private fun showCustomDialog() {
        val dialogView = LayoutInflater.from(requireContext())
            .inflate(R.layout.fragment_dialog_properties, null)

        val dialog = AlertDialog.Builder(requireContext()).setView(dialogView).create()

        val btnGoybolsun = dialogView.findViewById<Button>(R.id.btnGoybolsun)
        val btnKabulEt = dialogView.findViewById<Button>(R.id.btnKabulEt)
        val recyclerView = dialogView.findViewById<RecyclerView>(R.id.rv_properties)
        val checkBox = dialogView.findViewById<CheckBox>(R.id.cbHemmesi)

        btnGoybolsun?.setOnClickListener {
            dialog.dismiss()
        }

        btnKabulEt?.setOnClickListener {
            dialog.dismiss()
        }

        val items = listOf(
            PropertiesDialogData("Kwartira", R.drawable.kwartira),
            PropertiesDialogData("Kottej", R.drawable.kottej),
            PropertiesDialogData("Elitka", R.drawable.elitka),
            PropertiesDialogData("Polelitka", R.drawable.ic_floor_elite),
            PropertiesDialogData("Daça", R.drawable.dacha),
            PropertiesDialogData("Plan jaý", R.drawable.ic_flat)
        )

        val adapter = PropertiesDialogAdapter(items) { selectedItem ->
            Toast.makeText(requireContext(), "Выбрано: ${selectedItem.name}", Toast.LENGTH_SHORT)
                .show()
        }

        recyclerView.adapter = adapter
        recyclerView.layoutManager = GridLayoutManager(requireContext(), 2)

        checkBox.setOnCheckedChangeListener { _, isChecked ->
            adapter.setAllSelected(isChecked)
        }

        dialog.show()
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private fun RemontDialog() {
        val dialogView =
            LayoutInflater.from(requireContext()).inflate(R.layout.fragment_dialog_remont, null)
        val dialog = AlertDialog.Builder(requireContext()).setView(dialogView).create()

        val btnGoybolsun = dialogView.findViewById<Button>(R.id.btnGoybolsun)
        val btnKabulEt = dialogView.findViewById<Button>(R.id.btnKabulEt)
        val buttons = listOf(
            dialogView.findViewById<TextView>(R.id.euro_remont),
            dialogView.findViewById(R.id.remont_cosmetic),
            dialogView.findViewById(R.id.remont_designer),
            dialogView.findViewById(R.id.gos_remont),
            dialogView.findViewById(R.id.normal_remont),
            dialogView.findViewById(R.id.remont_etmeli)
        )

        val cbHemmesi = dialogView.findViewById<CheckBox>(R.id.cbHemmesi)

        fun toggleAllButtons() {
            val background = R.drawable.bg_selected_properties_btn
            buttons.forEach { button ->
                button?.setBackgroundResource(if (cbHemmesi.isChecked) background else R.drawable.emlakler_btn_bg)
            }
        }

        buttons.forEach { button ->
            button?.setOnClickListener {
                val selectedState = R.drawable.bg_selected_properties_btn
                val defaultState = R.drawable.emlakler_btn_bg

                button.setBackgroundResource(
                    if (button.background.constantState == requireContext().getDrawable(selectedState)?.constantState)
                        defaultState else selectedState
                )

                cbHemmesi.isChecked = buttons.all {
                    it?.background?.constantState == requireContext().getDrawable(selectedState)?.constantState
                }
            }
        }

        cbHemmesi.setOnClickListener { toggleAllButtons() }

        btnGoybolsun.setOnClickListener { dialog.dismiss() }
        btnKabulEt.setOnClickListener { dialog.dismiss() }

        dialog.show()
    }

    private fun switchDesign() {
        binding.customSwitch.setOnCheckedChangeListener { _, isChecked ->
            val trackColor = if (isChecked) R.color.black else R.color.unchecked_track
            binding.customSwitch.trackTintList =
                ContextCompat.getColorStateList(requireContext(), trackColor)
            binding.customSwitch.thumbTintList =
                ContextCompat.getColorStateList(requireContext(), R.color.white)
        }
    }








}
