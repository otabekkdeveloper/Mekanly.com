package com.mekanly.presentation.ui.fragments.confirmation

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.mekanly.data.models.User
import com.mekanly.data.local.preferences.AppPreferences
import com.mekanly.domain.model.ResponseBodyState
import com.mekanly.databinding.FragmentConfirmationBinding
import com.mekanly.domain.useCase.ConfirmationUseCase
import com.mekanly.domain.useCase.LoginUseCase
import com.mekanly.ui.activities.MainActivity
import com.mekanly.utils.extensions.showErrorSnackBar
import com.mekanly.utils.extensions.showSuccessSnackBar

class FragmentConfirmation : Fragment() {
    private lateinit var binding: FragmentConfirmationBinding

    private val loginUseCase by lazy { LoginUseCase() }
    private val confirmationUseCase by lazy { ConfirmationUseCase() }

    private val args by navArgs<FragmentConfirmationArgs>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentConfirmationBinding.inflate(inflater, container, false)
        initListeners()
        return binding.root
    }

    private fun initListeners() {
        binding.btnSendEmptyMessage.setOnClickListener {
            confirmationUseCase.invoke(
                args.phone, args.tokenWaitlist, binding.inputOtp.text.toString()
            ) {
                when (it) {
                    is ResponseBodyState.Error -> {
                        binding.progressBar.visibility = View.GONE
                        requireContext().showErrorSnackBar(binding.root, "Login unsuccessful")
                    }

                    ResponseBodyState.Loading -> {
                        binding.progressBar.visibility = View.VISIBLE
                    }

                    is ResponseBodyState.Success -> {
                        it.dataResponse as User
                        binding.progressBar.visibility = View.GONE
                        requireContext().showSuccessSnackBar(binding.root, "Login Success")
                        AppPreferences.setToken(it.dataResponse.token)
                        AppPreferences.setUsername(it.dataResponse.phone)
                        val intent = Intent(requireContext(), MainActivity::class.java)
                        startActivity(intent)
                        requireActivity().finish()
                    }

                    else -> {}
                }
            }
        }
    }
}



