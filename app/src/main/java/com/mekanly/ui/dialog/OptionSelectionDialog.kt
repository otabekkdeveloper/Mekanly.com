package com.mekanly.ui.dialog

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View.GONE
import androidx.annotation.StringRes
import androidx.recyclerview.widget.GridLayoutManager
import com.mekanly.data.models.Option
import com.mekanly.databinding.FragmentDialogPropertiesBinding

class OptionSelectionDialog(
    context: Context,
    type:Int,
    @StringRes title:Int,
    singleSelection:Boolean,
    items: List<Option>,
    selectedItems: List<Option> = emptyList(),
    onConfirm: (List<Option>) -> Unit
) {
    private val binding: FragmentDialogPropertiesBinding =
        FragmentDialogPropertiesBinding.inflate(LayoutInflater.from(context))

    private val dialog: AlertDialog = AlertDialog.Builder(context)
        .setView(binding.root)
        .setCancelable(true)
        .create()

    init {
        val adapter = OptionsDialogAdapter(type,items, singleSelection)

        binding.tvTitle.text = context.getText(title)
        binding.btnGoybolsun.setOnClickListener {
            dialog.dismiss()
        }

        binding.btnKabulEt.setOnClickListener {
            onConfirm(adapter.getSelectedItems())
            dialog.dismiss()
        }

        adapter.setSelectedItems(selectedItems)
        binding.rvProperties.adapter = adapter
        binding.rvProperties.layoutManager = GridLayoutManager(context, 2)

        if (singleSelection){
            binding.cbHemmesi.visibility = GONE
        }
        binding.cbHemmesi.setOnCheckedChangeListener { _, isChecked ->
            adapter.setAllSelected(isChecked)
        }
    }

    fun show() {
        dialog.show()
    }
}
