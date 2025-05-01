package com.mekanly.ui.fragments.profile

import LocationBottomSheet
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.graphics.Paint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.mekanly.R
import com.mekanly.data.local.preferences.AppPreferences
import com.mekanly.databinding.FragmentProfileBinding
import com.mekanly.helpers.PreferencesHelper
import com.mekanly.ui.fragments.language.LanguageDialogFragment
import com.mekanly.ui.fragments.search.viewModel.VMSearch
import com.mekanly.ui.login.LoginActivity

class FragmentProfile : Fragment() {
    private lateinit var binding: FragmentProfileBinding
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
                tvAccountNumber.paintFlags = tvAccountNumber.paintFlags or Paint.UNDERLINE_TEXT_FLAG
                tvAccountNumber.text = getString(R.string.press_to_log_in)
                tvAccountNumber.setTextColor(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.bg_blue_two
                    )
                )
            }
        } else {
            binding.apply {
                tvAccountName.text = getString(R.string.account)
                btnLogout.visibility = View.VISIBLE
                dividerLogOut.visibility = View.VISIBLE
                tvAccountNumber.text = AppPreferences.getUsername()
                tvAccountNumber.setTextColor(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.text_color_gray
                    )
                )
            }
        }
    }

    private fun initListeners() {

        binding.buttonSupport.setOnClickListener {
            findNavController().navigate(R.id.action_fragmentHome_to_hintFragment)
        }

        binding.btnLanguage.setOnClickListener {

            val languageDialog = LanguageDialogFragment()
            languageDialog.show(childFragmentManager, "LanguageDialogFragment")

        }

        binding.btnAddHouse.setOnClickListener {
            findNavController().navigate(R.id.action_fragmentHome_to_addNotificationFragment)
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

        binding.btnLogout.setOnClickListener{
            showLogoutDialog()
        }

        if (!PreferencesHelper.getGlobalOptions()?.locations.isNullOrEmpty()) {
            val cities = PreferencesHelper.getGlobalOptions()?.locations!!
            binding.btnLocation.setOnClickListener {
                val bottomSheet = LocationBottomSheet(cities, onDelete = {
                    binding.apply { textLocation.text = "" }
                }) { selectedCity ->
                    binding.apply { textLocation.text = selectedCity.name }
                    viewModel.getHouses()
                }
                bottomSheet.show(childFragmentManager, "LocationBottomSheet")
            }

        }

    }

    private fun showLogoutDialog() {
        AlertDialog.Builder(requireContext())
            .setTitle(getString(R.string.logout))
            .setMessage(getString(R.string.logout_question))
            .setPositiveButton(getString(R.string.yes)) { _, _ ->
                logout()
            }
            .setNegativeButton(getString(R.string.cancel), null)
            .show()
    }

    private fun logout() {
        AppPreferences.clearPreferences()
        val intent = requireContext().packageManager.getLaunchIntentForPackage(requireContext().packageName)
        intent?.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
        intent?.let { startActivity(it) }
        requireActivity().finishAffinity()
    }
}
