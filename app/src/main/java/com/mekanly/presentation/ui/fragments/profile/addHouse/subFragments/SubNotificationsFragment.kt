package com.mekanly.presentation.ui.fragments.profile.addHouse.subFragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.chip.Chip
import com.mekanly.R
import com.mekanly.databinding.FragmentSubNotificationsBinding


class SubNotificationsFragment : Fragment() {
    private lateinit var binding: FragmentSubNotificationsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentSubNotificationsBinding.inflate(inflater, container, false)


        binding.chipGroups.isSingleSelection = true // Включаем одиночный выбор

        binding.chipGroups.setOnCheckedStateChangeListener { group, checkedIds ->
            for (i in 0 until group.childCount) {
                val chip = group.getChildAt(i) as Chip
                val isActive = checkedIds.contains(chip.id) // Проверяем, выбран ли чип

                chip.chipBackgroundColor = ContextCompat.getColorStateList(
                    requireContext(), if (isActive) R.color.black else R.color.white
                )
                chip.setTextColor(
                    ContextCompat.getColor(requireContext(), if (isActive) R.color.white else R.color.black)
                )
                chip.chipStrokeColor = ContextCompat.getColorStateList(requireContext(), R.color.black) // Обводка всегда черная
            }
        }


        binding.addHouse.setOnClickListener{

            findNavController().navigate(R.id.action_addNotificationFragment_to_fragmentAddHouse)
        }




        return binding.root
    }





    }
