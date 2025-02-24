package com.mekanly.presentation.ui.fragments.language

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.mekanly.LanguageManager
import com.mekanly.PreferencesHelper
import com.mekanly.R
import com.mekanly.databinding.FragmentLanguageBinding

class LanguageFragment : Fragment() {
    private lateinit var binding: FragmentLanguageBinding
    private lateinit var preferencesHelper: PreferencesHelper

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLanguageBinding.inflate(inflater, container, false)

        // Инициализация PreferencesHelper
        preferencesHelper = PreferencesHelper(requireContext())

        // Установить язык по умолчанию, если не выбран ранее
        val currentLanguage = preferencesHelper.getLanguage() ?: "tk" // Язык по умолчанию

        // Установить выбранную радиокнопку
        val radioGroup = binding.languageRadioGroup
        when (currentLanguage) {
            "ru" -> binding.radioRussian.isChecked = true
            "tk" -> binding.radioTurkmen.isChecked = true
        }

        // Обработчик выбора языка
        radioGroup.setOnCheckedChangeListener { _, checkedId ->
            val newLanguageCode = when (checkedId) {
                R.id.radioRussian -> "ru"
                R.id.radioTurkmen -> "tk"
                else -> null
            }

            newLanguageCode?.let {
                if (it != currentLanguage) { // Перезагружать только если язык изменился
                    preferencesHelper.setLanguage(it)
                    LanguageManager.setLocale(requireContext(), it)
                    requireActivity().recreate() // Перезагрузить Activity
                } else {
                    Toast.makeText(requireContext(), "Язык уже установлен", Toast.LENGTH_SHORT).show()
                }
            }
        }


        binding.backBtn.setOnClickListener{
            findNavController().popBackStack()
        }



        return binding.root
    }
}
