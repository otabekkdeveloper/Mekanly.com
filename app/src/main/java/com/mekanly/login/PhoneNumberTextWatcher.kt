package com.mekanly.login

import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.EditText

class PhoneNumberTextWatcher(
    private val editText: EditText,
    private val callback: PhoneNumberValidationCallback
) : TextWatcher {

    private var isFormatting: Boolean = false
    private val phonePrefix = "+993 "
    private val minPhoneLength = phonePrefix.length

    init {
        editText.onFocusChangeListener = View.OnFocusChangeListener { _, hasFocus ->
            val currentText = editText.text.toString()
            if (hasFocus) {
                if (!currentText.startsWith(phonePrefix)) {
                    editText.setText(phonePrefix)
                    safeSetSelection(editText, phonePrefix.length)
                }
            } else {
                if (currentText == phonePrefix || currentText.isEmpty()) {
                    editText.setText("")
                    editText.hint = "Enter phone number"
                }
            }
        }
    }

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        if (!isFormatting) {
            editText.error = null
            val text = s?.toString() ?: ""
            if (text.isNotEmpty()) {
                val currentPosition = editText.selectionStart
                if (currentPosition < minPhoneLength) {
                    editText.post {
                        safeSetSelection(editText, minPhoneLength)
                    }
                }
            }
        }
    }

    override fun afterTextChanged(editable: Editable?) {
        if (isFormatting) return
        try {
            isFormatting = true
            val text = editable?.toString() ?: ""
            if (text.length < minPhoneLength || !text.startsWith(phonePrefix)) {
                editText.setText(phonePrefix)
                safeSetSelection(editText, minPhoneLength)
                isFormatting = false
                return
            }
            val numberPart = text.substring(minPhoneLength)
            val digitsOnly = numberPart.filter { it.isDigit() }
            val validNumber = when {
                digitsOnly.isEmpty() -> ""
                digitsOnly[0] == '6' -> {
                    if (digitsOnly.length > 1 && digitsOnly[1] !in '1'..'5') {
                        digitsOnly.substring(0, 1)
                    } else digitsOnly
                }
                digitsOnly[0] == '7' -> {
                    if (digitsOnly.length > 1 && digitsOnly[1] != '1') {
                        digitsOnly.substring(0, 1)
                    } else digitsOnly
                }
                else -> ""
            }.take(8)
            val formatted = validNumber.chunked(2)
                .filter { it.isNotEmpty() }
                .joinToString(" ")
            val finalText = phonePrefix + formatted
            if (text != finalText) {
                editText.setText(finalText)
                safeSetSelection(editText, finalText.length)
            }
            if (validNumber.length == 8) {
                callback.onPhoneNumberValid()
            } else {
                callback.onPhoneNumberInvalid()
            }
        } finally {
            isFormatting = false
        }
    }

    private fun safeSetSelection(editText: EditText, position: Int) {
        try {
            val safePosition = position.coerceIn(0, editText.length())
            editText.setSelection(safePosition)
        } catch (e: Exception) {
        }
    }

    interface PhoneNumberValidationCallback {
        fun onPhoneNumberValid()
        fun onPhoneNumberInvalid()
    }
}
