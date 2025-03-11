package com.mekanly.presentation.ui.fragments.addHouse

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mekanly.R
import com.mekanly.data.PropertiesDialogData
import com.mekanly.data.requestBody.RequestBodyAddHouse
import com.mekanly.data.responseBody.ResponseBodyState
import com.mekanly.databinding.FragmentAddHouseBinding
import com.mekanly.presentation.ui.dialog.propertiesDialog.PropertiesDialogAdapter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class FragmentAddHouse : Fragment() {

    private lateinit var binding: FragmentAddHouseBinding

    private val viewModel:VMAddHouse by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentAddHouseBinding.inflate(inflater, container, false)
        switchDesign()
        initListeners()
        return binding.root
    }

    private fun initListeners() {
        binding.propertiesBtn.setOnClickListener {
            showCustomDialog()
        }

        binding.remont.setOnClickListener {
            RemontDialog()
        }

        binding.mumkinchilikler.setOnClickListener {
            opportunityDialog()
        }

        binding.back.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.location.setOnClickListener{
            openLocationSelector()
        }
        binding.btnDone.setOnClickListener{
            val requestAddHouse = RequestBodyAddHouse(binding.edtName.text.toString(),binding.edtDescription.text.toString(),0,0,
                emptyList(), emptyList()
            )
            lifecycleScope.launch (Dispatchers.IO){
                viewModel.addHouse(requestAddHouse){
                when(it){
                    is ResponseBodyState.Error -> {
                        Log.e("ADD_HOUSE", "initListeners: "+it.error )
                    }
                    ResponseBodyState.Loading -> {
                        Log.e("ADD_HOUSE", "initListeners: loading" )
                    }
                    is ResponseBodyState.Success -> {
                        Log.e("ADD_HOUSE", "Success" )
                    }
                    else -> {}
                }
                }
            }

        }
    }

    private fun openLocationSelector() {

    }


    @SuppressLint("UseCompatLoadingForDrawables")
    private fun showCustomDialog() {
        val currentContext = requireContext()
        val dialogView =
            LayoutInflater.from(currentContext).inflate(R.layout.fragment_dialog_properties, null)

        val dialog = AlertDialog.Builder(currentContext).setView(dialogView).create()

        val btnGoybolsun = dialogView.findViewById<Button>(R.id.btnGoybolsun)
        val btnKabulEt = dialogView.findViewById<Button>(R.id.btnKabulEt)
        val recyclerView = dialogView.findViewById<RecyclerView>(R.id.rv_properties)
        val checkBox = dialogView.findViewById<CheckBox>(R.id.cbHemmesi)

        btnGoybolsun?.setOnClickListener {
            Toast.makeText(currentContext, "Отмена", Toast.LENGTH_SHORT).show()
            dialog.dismiss()
        }

        btnKabulEt?.setOnClickListener {
            Toast.makeText(currentContext, "Принято", Toast.LENGTH_SHORT).show()
            dialog.dismiss()
        }

        if (recyclerView != null) {
            val items = listOf(
                PropertiesDialogData("Kwartira", R.drawable.kwartira),
                PropertiesDialogData("Kottej", R.drawable.kottej),
                PropertiesDialogData("Elitka", R.drawable.elitka),
                PropertiesDialogData("Polelitka", R.drawable.ic_floor_elite),
                PropertiesDialogData("Daça", R.drawable.dacha),
                PropertiesDialogData("Plan jaý", R.drawable.ic_flat)
            )

            val adapter = PropertiesDialogAdapter(items) { selectedItem ->


                Toast.makeText(
                    currentContext,
                    "Maks sen ${selectedItem.name} bolümini sayladyň",
                    Toast.LENGTH_SHORT
                ).show()
            }

            recyclerView.adapter = adapter
            recyclerView.layoutManager = GridLayoutManager(currentContext, 2)


            checkBox.setOnCheckedChangeListener { _, isChecked ->
                adapter.setAllSelected(isChecked)
            }

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
        "UseCompatLoadingForDrawables", "MissingInflatedId", "InflateParams", "CutPasteId"
    )

    private fun opportunityDialog() {
        val dialogView = LayoutInflater.from(requireContext())
            .inflate(R.layout.fragment_dialog_mumkinchilikler, null)

        AlertDialog.Builder(requireContext()).setView(dialogView).setCancelable(true).create()
            .show()
    }


    private fun switchDesign() {

        binding.customSwitch.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                binding.customSwitch.trackTintList =
                    ContextCompat.getColorStateList(requireContext(), R.color.black)
                binding.customSwitch.trackDecorationTintList =
                    ContextCompat.getColorStateList(requireContext(), R.color.color_transparent)
                binding.customSwitch.thumbTintList =
                    ContextCompat.getColorStateList(requireContext(), R.color.white)
                binding.customSwitch.thumbIconSize = 200

            } else {

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


        binding.customSwitchThree.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {

                binding.apply {
                    customSwitchThree.trackTintList =
                        ContextCompat.getColorStateList(requireContext(), R.color.black)
                    customSwitchThree.trackDecorationTintList =
                        ContextCompat.getColorStateList(requireContext(), R.color.color_transparent)
                    customSwitchThree.thumbTintList =
                        ContextCompat.getColorStateList(requireContext(), R.color.white)
                    customSwitchThree.thumbIconSize = 100
                }


            } else {

                binding.apply {

                    customSwitchThree.trackTintList =
                        ContextCompat.getColorStateList(requireContext(), R.color.unchecked_track)
                    customSwitchThree.trackDecorationTintList =
                        ContextCompat.getColorStateList(requireContext(), R.color.color_transparent)
                    customSwitchThree.thumbTintList =
                        ContextCompat.getColorStateList(requireContext(), R.color.white)


                }
            }
        }
    }
}








