package com.mekanly.data.local.preferences

import android.content.Context
import android.content.SharedPreferences

class AppPreferences(private val context: Context) {

    companion object {
        private const val PREF_TOKEN = "pref_token"
        private const val PREF_USERNAME = "pref_username"
        private const val TOKEN_ON_WAITLIST = "pref_token_on_waitlist"
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

    var tokenOnWaitlist: String
        get() = sharedPreferences.getString(TOKEN_ON_WAITLIST, "") ?: ""
        set(value) = sharedPreferences.edit().putString(TOKEN_ON_WAITLIST, value).apply()


    var username: String
        get() = sharedPreferences.getString(PREF_USERNAME, "") ?: ""
        set(value) = sharedPreferences.edit().putString(PREF_USERNAME, value).apply()

}