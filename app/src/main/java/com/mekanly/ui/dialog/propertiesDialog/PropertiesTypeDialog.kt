package com.mekanly.presentation.ui.dialog.propertiesDialog

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import com.mekanly.R
import com.mekanly.data.PropertiesDialogData
import com.mekanly.databinding.FragmentDialogPropertiesBinding

class PropertiesTypeDialog(context: Context) {
    private val binding: FragmentDialogPropertiesBinding =
        FragmentDialogPropertiesBinding.inflate(LayoutInflater.from(context))

    private val dialog: AlertDialog = AlertDialog.Builder(context)
        .setView(binding.root)
        .setCancelable(true) // Можно закрыть по клику вне диалога
        .create()

    init {
        binding.btnGoybolsun.setOnClickListener {
            Toast.makeText(context, "Отмена", Toast.LENGTH_SHORT).show()
            dialog.dismiss()
        }

        binding.btnKabulEt.setOnClickListener {
            Toast.makeText(context, "Принято", Toast.LENGTH_SHORT).show()
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
            Toast.makeText(
                context,
                "Maks sen ${selectedItem.name} bolümini sayladyň",
                Toast.LENGTH_SHORT
            ).show()
        }

        binding.rvProperties.adapter = adapter
        binding.rvProperties.layoutManager = GridLayoutManager(context, 2)

        binding.cbHemmesi.setOnCheckedChangeListener { _, isChecked ->
            adapter.setAllSelected(isChecked)
        }
    }

    fun show() {
        dialog.show()
    }
}
