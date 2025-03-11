package com.mekanly.data.local.preferences

import android.content.Context
import android.content.SharedPreferences

class AppPreferences(private val context: Context) {

    companion object {
        private const val PREF_TOKEN = "pref_token"
    }

    private val defaultPreferenceName by lazy {
        context.packageName + "_preferences"
    }

    private val sharedPreferences: SharedPreferences by lazy {
        context.getSharedPreferences(defaultPreferenceName, Context.MODE_PRIVATE)
    }

    var token: String
        get() = sharedPreferences.getString(PREF_TOKEN, "") ?: ""
        set(value) = sharedPreferences.edit().putString(PREF_TOKEN, value).apply()
}