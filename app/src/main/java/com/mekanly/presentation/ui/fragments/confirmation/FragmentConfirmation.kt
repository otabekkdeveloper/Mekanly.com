package com.mekanly.presentation.ui.fragments.confirmation

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.mekanly.data.dataModels.DataUser
import com.mekanly.data.local.preferences.AppPreferences
import com.mekanly.data.responseBody.ResponseBodyState
import com.mekanly.databinding.FragmentConfirmationBinding
import com.mekanly.domain.useCase.UseCaseLogin
import com.mekanly.login.LoginActivity
import com.mekanly.presentation.ui.StaticFunctions.showErrorSnackBar
import com.mekanly.presentation.ui.StaticFunctions.showSuccessSnackBar
import com.mekanly.presentation.ui.activities.main.MainActivity

class FragmentConfirmation : Fragment() {
    private lateinit var binding: FragmentConfirmationBinding

    private val useCase by lazy {
        UseCaseLogin()
    }

    private val appPrefs by lazy {
        AppPreferences(requireContext())
    }
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
            useCase.executeConfirmation(
                args.phone, args.tokenWaitlist, binding.inputOtp.text.toString()
            ) {
                when (it) {
                    is ResponseBodyState.Error -> {
                        binding.progressBar.visibility = View.GONE
                        showErrorSnackBar(requireContext(), binding.root, "Login unsuccessful")
                    }

                    ResponseBodyState.Loading -> {
                        binding.progressBar.visibility = View.VISIBLE
                    }

                    is ResponseBodyState.Success -> {
                        it.dataResponse as DataUser
                        binding.progressBar.visibility = View.GONE
                        showSuccessSnackBar(requireContext(), binding.root, "Login Success")
                        appPrefs.token = it.dataResponse.token
                        appPrefs.username = it.dataResponse.phone
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



