package com.mekanly.ui.fragments.language

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.mekanly.helpers.LanguageManager
import com.mekanly.helpers.PreferencesHelper
import com.mekanly.R
import com.mekanly.databinding.FragmentLanguageBinding

class LanguageDialogFragment : DialogFragment() {
    private var _binding: FragmentLanguageBinding? = null
    private val binding get() = _binding!!
    private lateinit var preferencesHelper: PreferencesHelper

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        _binding = FragmentLanguageBinding.inflate(LayoutInflater.from(context))

        preferencesHelper = PreferencesHelper

        val currentLanguage = preferencesHelper.getLanguage() ?: "tk"

        // Установить выбранную радиокнопку
        when (currentLanguage) {
            "ru" -> binding.radioRussian.isChecked = true
            "tk" -> binding.radioTurkmen.isChecked = true
        }

        binding.languageRadioGroup.setOnCheckedChangeListener { _, checkedId ->
            val newLanguageCode = when (checkedId) {
                R.id.radioRussian -> "ru"
                R.id.radioTurkmen -> "tk"
                else -> null
            }

            newLanguageCode?.let {
                if (it != currentLanguage) {
                    preferencesHelper.setLanguage(it)
                    LanguageManager.setLocale(requireContext(), it)
                    requireActivity().recreate()
                    dismiss()
                } else {
                    Toast.makeText(requireContext(), "Язык уже установлен", Toast.LENGTH_SHORT).show()
                }
            }
        }



        return AlertDialog.Builder(requireContext())
            .setView(binding.root)
            .create()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
