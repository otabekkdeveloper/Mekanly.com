package com.mekanly.presentation.ui.fragments.register

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.mekanly.R
import com.mekanly.data.models.User
import com.mekanly.data.local.preferences.AppPreferences
import com.mekanly.domain.model.ResponseBodyState
import com.mekanly.databinding.FragmentSignUpBinding
import com.mekanly.ui.login.PhoneNumberTextWatcher
import com.mekanly.utils.extensions.showErrorSnackBar
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class FragmentRegister : Fragment(), PhoneNumberTextWatcher.PhoneNumberValidationCallback {
    private lateinit var binding: FragmentSignUpBinding
    private var isPhoneValid: Boolean = false
    private val viewModel: VMRegister by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?

    ): View {
        binding = FragmentSignUpBinding.inflate(inflater, container, false)
        initListeners()
        observeViewModel()
        return binding.root
    }

    private fun observeViewModel() {
        lifecycleScope.launch {
            viewModel.registerState.collectLatest {
                when (it) {
                    is ResponseBodyState.Error -> {
                        binding.progressBar.visibility = View.GONE
                    }

                    ResponseBodyState.Loading -> {
                        binding.progressBar.visibility = View.VISIBLE
                    }

                    is ResponseBodyState.Success -> {
                        it.dataResponse as User
                        AppPreferences(requireContext()).tokenOnWaitlist = it.dataResponse.token
                        binding.progressBar.visibility = View.GONE
                        val action =
                            FragmentRegisterDirections.actionSignUpFragmentToFragmentConfirmation(
                                binding.inputPhone.text.toString(), it.dataResponse.token
                            )

                        findNavController().navigate(action)
                    }

                    else -> {}
                }
            }
        }
    }

    private fun initListeners() {
        binding.btnConfimation.setOnClickListener {
            if (isPhoneValid && binding.btnCheckVerification.isChecked) {
                viewModel.login(binding.inputPhone.text.toString())
            }else{
                requireContext().showErrorSnackBar(binding.root,
                    getString(R.string.error_message_phone))
            }
        }
        binding.inputPhone.addTextChangedListener(
            PhoneNumberTextWatcher(
                binding.inputPhone, this
            )
        )
    }

    override fun onPhoneNumberValid() {
        isPhoneValid = true
        setRegisterButtonState()
    }

    private fun setRegisterButtonState() {

    }

    override fun onPhoneNumberInvalid() {
        isPhoneValid = false
        setRegisterButtonState()
    }
}



