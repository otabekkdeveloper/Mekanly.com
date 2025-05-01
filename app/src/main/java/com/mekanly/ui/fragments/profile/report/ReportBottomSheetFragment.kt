package com.example.myapp

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.mekanly.R

class ReportBottomSheetFragment : BottomSheetDialogFragment() {

    private lateinit var radioGroup: RadioGroup
    private lateinit var submitButton: Button

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_report_bottom_sheet, container, false)

        radioGroup = view.findViewById(R.id.radioGroup)
        submitButton = view.findViewById(R.id.submitButton)

        submitButton.setOnClickListener {
            val selectedId = radioGroup.checkedRadioButtonId
            if (selectedId != -1) {
                val radioButton = view.findViewById<RadioButton>(selectedId)
                val selectedReason = radioButton.text.toString()
                // Здесь можно обработать выбранную причину жалобы
                // например, отправить на сервер
                dismiss()

                // Показываем диалог подтверждения
                showConfirmationDialog()
            }
        }

        return view
    }

    private fun showConfirmationDialog() {
        // Создаем кастомный макет для AlertDialog
        val dialogView = LayoutInflater.from(requireContext()).inflate(R.layout.dialog_report_confirmation, null)
        val okButton = dialogView.findViewById<Button>(R.id.btnOk)

        // Создаем AlertDialog с кастомным макетом
        val alertDialog = AlertDialog.Builder(requireContext())
            .setView(dialogView)
            .setCancelable(false)
            .create()

        // Настраиваем прозрачный фон, чтобы видеть наш кастомный CardView
        alertDialog.window?.setBackgroundDrawableResource(android.R.color.transparent)

        // Настраиваем нажатие на кнопку OK
        okButton.setOnClickListener {
            alertDialog.dismiss()
        }

        // Показываем диалог
        alertDialog.show()
    }

    companion object {
        const val TAG = "ReportBottomSheetFragment"

        fun newInstance(): ReportBottomSheetFragment {
            return ReportBottomSheetFragment()
        }
    }
}

