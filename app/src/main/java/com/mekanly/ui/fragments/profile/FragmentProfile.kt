package com.mekanly.presentation.ui.fragments.profile

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
import com.mekanly.databinding.ActivityLoginBinding
import com.mekanly.databinding.FragmentProfileBinding
import com.mekanly.ui.login.LoginActivity
import com.mekanly.presentation.ui.activities.main.MainActivity
import com.mekanly.presentation.ui.fragments.flow.FragmentFlow
import com.mekanly.presentation.ui.fragments.flow.VMFlow
import com.mekanly.presentation.ui.fragments.home.FragmentHome
import com.mekanly.presentation.ui.fragments.search.viewModel.VMSearch
import com.mekanly.presentation.ui.fragments.search.viewModel.VMSearch.Companion.FILTER_TYPE_LOCATION

class FragmentProfile : Fragment() {
    private lateinit var binding: FragmentProfileBinding
    private val vmFlow: VMFlow by activityViewModels()
    private val viewModel: VMSearch by viewModels()
    private val appPrefs by lazy {
        AppPreferences(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentProfileBinding.inflate(inflater, container, false)
        initView()
        initListeners()
        return binding.root
    }

    private fun initView() {
        if (appPrefs.token == "") {
            binding.apply {
                tvAccountName.text = "Press to log in"
                tvAccountNumber.text = ""
            }
        } else {
            binding.apply {
                tvAccountName.text = "Akkaunt"
                tvAccountNumber.text = appPrefs.username
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

        binding.crAccount.setOnClickListener {
            if (appPrefs.token == "") {
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
