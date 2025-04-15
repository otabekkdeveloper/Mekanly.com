package com.mekanly.ui.fragments.profile

import LocationBottomSheet
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.mekanly.R
import com.mekanly.data.local.preferences.AppPreferences
import com.mekanly.databinding.FragmentProfileBinding
import com.mekanly.ui.login.LoginActivity
import com.mekanly.ui.fragments.flow.VMFlow
import com.mekanly.ui.fragments.search.viewModel.VMSearch
import com.mekanly.ui.fragments.search.viewModel.VMSearch.Companion.FILTER_TYPE_LOCATION

class FragmentProfile : Fragment() {
    private lateinit var binding: FragmentProfileBinding
    private val vmFlow: VMFlow by activityViewModels()
    private val viewModel: VMSearch by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentProfileBinding.inflate(inflater, container, false)
        initView()
        initListeners()
        return binding.root
    }

    private fun initView() {
        if (AppPreferences.getToken() == "") {
            binding.apply {
                tvAccountName.text = getString(R.string.account)
                tvAccountNumber.text = getString(R.string.press_to_log_in)
                tvAccountNumber.setTextColor(ContextCompat.getColor(requireContext(), R.color.bg_blue_two))
            }
        } else {
            binding.apply {
                tvAccountName.text = "Akkaunt"
                tvAccountNumber.text = AppPreferences.getUsername()
                tvAccountNumber.setTextColor(ContextCompat.getColor(requireContext(), R.color.text_color_gray))
            }
        }
    }

    private fun initListeners() {

        binding.hintFragment.setOnClickListener {
            findNavController().navigate(R.id.action_fragmentHome_to_hintFragment)
        }

        binding.btnLanguage.setOnClickListener {
            findNavController().navigate((R.id.action_homeFragment_to_languageFragment))
        }

        binding.addHouse.setOnClickListener {
            findNavController().navigate(R.id.action_fragmentHome_to_addNotificationFragment)
        }

        binding.addNotifications.setOnClickListener {

        }

        binding.crAccount.setOnClickListener {
            if (AppPreferences.getToken() == "") {
                val intent = Intent(requireContext(), LoginActivity::class.java)
                startActivity(intent)
            } else {
                Toast.makeText(requireContext(), "You have already logged in", Toast.LENGTH_SHORT)
                    .show()
            }

        }

        if (vmFlow.globalState.value.locations.isNotEmpty()) {
            val cities = vmFlow.globalState.value.locations

            binding.btnLocation.setOnClickListener {
                val bottomSheet = LocationBottomSheet(cities, onDelete = {
                    binding.apply {
                        textLocation.text = ""
                    }
                }) { selectedCity ->
                    binding.apply {
                        textLocation.text = selectedCity.name
                    }
                    viewModel.updateFilterType(FILTER_TYPE_LOCATION)
                    viewModel.getPageInfoDefault(0, location = selectedCity)
                }

                bottomSheet.show(childFragmentManager, "LocationBottomSheet")
            }

        }

    }
}
