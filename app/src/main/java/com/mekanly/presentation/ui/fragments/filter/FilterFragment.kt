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
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
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

        }
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
            if (cbHemmesi.isChecked){
                buttons.forEach { button ->
                    button.setBackgroundResource(R.drawable.bg_selected_properties_btn)
                }
            }else{
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


        fun toggleAllButtons(){
            buttons.forEach { button ->
                if (cbHemmesi.isChecked) {
                    button.setBackgroundResource(R.drawable.bg_selected_properties_btn)
                } else {
                    button.setBackgroundResource(R.drawable.emlakler_btn_bg)
                }
            }}



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

    @SuppressLint("UseCompatLoadingForDrawables", "MissingInflatedId", "InflateParams",
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




