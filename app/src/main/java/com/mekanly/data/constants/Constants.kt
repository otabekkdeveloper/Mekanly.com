package com.mekanly.data.constants

import android.content.Context
import com.mekanly.R

class Constants {
    companion object {
        const val NO_CONTENT = 1
        const val UNSUCCESSFUL_RESPONSE = 2
        const val RESPONSE_FAILURE = 3

        fun getErrorMessageUpToType(context: Context, errorType: Int): String {
            return when (errorType) {
                NO_CONTENT -> context.getString(R.string.error_no_content)
                UNSUCCESSFUL_RESPONSE -> context.getString(R.string.error_failure)
                RESPONSE_FAILURE -> context.getString(R.string.error_no_internet)
                else -> context.getString(R.string.error_no_content)
            }
        }
    }



}