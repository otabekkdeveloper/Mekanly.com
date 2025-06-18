package com.mekanly.utils.extensions

import android.content.Context
import android.util.Log
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import com.google.android.material.snackbar.Snackbar
import android.view.View
import com.mekanly.R

fun Context.showErrorSnackBar(view: View?, message: String) {
    val parentView = findSuitableParent(view)

    if (parentView != null) {
        val snackBar = Snackbar.make(parentView, message, Snackbar.LENGTH_SHORT)

        val snackBarView = snackBar.view
        val params = snackBarView.layoutParams as ViewGroup.MarginLayoutParams
        params.setMargins(24, 0, 24, 50)
        snackBarView.layoutParams = params

        snackBar.setBackgroundTint(ContextCompat.getColor(this, R.color.error))
        snackBar.setTextColor(ContextCompat.getColor(this, R.color.white))
        snackBar.show()
    } else {
        Log.e("SnackbarError", "No suitable parent found for Snackbar")
    }
}

fun findSuitableParent(view: View?): View? {
    var current = view
    while (current != null) {
        if (current is ViewGroup &&
            (current.id == android.R.id.content || current is androidx.coordinatorlayout.widget.CoordinatorLayout)) {
            return current
        }
        val parent = current.parent
        current = if (parent is View) parent else null
    }
    return null
}


fun Context.showSuccessSnackBar(view: View, message: String) {
    val snackBar = Snackbar.make(view, message, Snackbar.LENGTH_SHORT)

    val snackBarView = snackBar.view
    val params = snackBarView.layoutParams as ViewGroup.MarginLayoutParams
    params.setMargins(24, 0, 24, 50)
    snackBarView.layoutParams = params

    snackBar.setBackgroundTint(ContextCompat.getColor(this, R.color.bg_app))
    snackBar.setTextColor(ContextCompat.getColor(this, R.color.white))
    snackBar.show()
}

