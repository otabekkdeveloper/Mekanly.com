package com.mekanly.helpers

import android.content.Context
import android.content.SharedPreferences

class PreferencesHelper(context: Context) {
    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences("app_preferences", Context.MODE_PRIVATE)

    fun setLanguage(languageCode: String) {
        sharedPreferences.edit().putString("language", languageCode).apply()
    }

    fun getLanguage(): String {
        return sharedPreferences.getString("language", "en") ?: "en" // По умолчанию — английский
    }
}
