package com.mekanly.presentation.ui

import android.content.Context
import android.util.Log
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import com.google.android.material.snackbar.Snackbar
import android.view.View
import com.mekanly.R

object StaticFunctions {
    fun showErrorSnackBar(context: Context, view: View?, message: String) {
        view?.let {
            val snackBar = Snackbar.make(view, message, Snackbar.LENGTH_SHORT)

            val snackbarView = snackBar.view
            val params = snackbarView.layoutParams as ViewGroup.MarginLayoutParams
            params.setMargins(24, 0, 24, 50)
            snackbarView.layoutParams = params

            snackBar.setBackgroundTint(ContextCompat.getColor(context, R.color.error))
            snackBar.setTextColor(ContextCompat.getColor(context, R.color.white))
            snackBar.show()
        } ?: Log.e("SnackbarError", "Fragment view is null, cannot show Snackbar")

    }

    fun showSuccessSnackBar(context: Context, view: View, message: String) {
        val snackBar = Snackbar.make(view, message, Snackbar.LENGTH_SHORT)

        val snackbarView = snackBar.view
        val params = snackbarView.layoutParams as ViewGroup.MarginLayoutParams
        params.setMargins(24, 0, 24, 50)
        snackbarView.layoutParams = params

        snackBar.setBackgroundTint(ContextCompat.getColor(context, R.color.bg_app))
        snackBar.setTextColor(ContextCompat.getColor(context, R.color.white))
        snackBar.show()
    }
}

// Usage example:
// StaticFunctions.showSnackBar(requireContext(), binding.root, "Your message here")