package com.mekanly.presentation.ui.dialog.propertiesDialog

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.widget.Button
import android.widget.CheckBox
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.recyclerview.widget.GridLayoutManager
import com.mekanly.R
import com.mekanly.data.PropertiesDialogData
import com.mekanly.databinding.FragmentDialogPropertiesBinding
import com.mekanly.databinding.FragmentDialogRemontBinding

@SuppressLint("UseCompatLoadingForDrawables")
class RepairTypeDialog(context: Context) {
    private val binding: FragmentDialogRemontBinding =
        FragmentDialogRemontBinding.inflate(LayoutInflater.from(context))

    private val dialog: AlertDialog = AlertDialog.Builder(context)
        .setView(binding.root)
        .setCancelable(true) // Можно закрыть по клику вне диалога
        .create()

    init {


        val buttons = listOf(
            binding.euroRemont,
            binding.remontCosmetic,
            binding.remontDesigner,
            binding.gosRemont,
            binding.normalRemont,
            binding.remontEtmeli
        )


        fun toggleAllButtons() {
            buttons.forEach { button ->
                if (binding.cbHemmesi.isChecked) {
                    button.setBackgroundResource(R.drawable.bg_selected_properties_btn)
                } else {
                    button.setBackgroundResource(R.drawable.emlakler_btn_bg)
                }
            }
        }



        buttons.forEach { button ->
            button.setOnClickListener {

                if (button.background.constantState == context.getDrawable(R.drawable.bg_selected_properties_btn)?.constantState) {
                    button.setBackgroundResource(R.drawable.emlakler_btn_bg)
                } else {

                    button.setBackgroundResource(R.drawable.bg_selected_properties_btn)
                }


                if (buttons.any {
                        it.background.constantState != context.getDrawable(R.drawable.bg_selected_properties_btn)?.constantState
                    }) {
                    binding.cbHemmesi.isChecked = false
                }


                if (buttons.all {
                        it.background.constantState == context.getDrawable(R.drawable.bg_selected_properties_btn)?.constantState
                    }) {
                    binding.cbHemmesi.isChecked = true
                }
            }
        }

        binding.cbHemmesi.setOnClickListener {
            toggleAllButtons()
        }


        binding.btnGoybolsun.setOnClickListener {
            Toast.makeText(context, "Отмена", Toast.LENGTH_SHORT).show()
            dialog.dismiss()
        }

        binding.btnKabulEt.setOnClickListener {
            Toast.makeText(context, "Принято", Toast.LENGTH_SHORT).show()
            dialog.dismiss()
        }






    }

    fun show() {
        dialog.show()
    }
}
