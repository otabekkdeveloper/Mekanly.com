package com.mekanly.data.local.preferences

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit

object AppPreferences {

    private lateinit var sharedPreferences: SharedPreferences

    private const val PREF_TOKEN = "pref_token"
    private const val PREF_FIREBASE_TOKEN = "pref_firebase_token"
    private const val PREF_USERNAME = "pref_username"
    private const val TOKEN_ON_WAITLIST = "pref_token_on_waitlist"


    fun initialize(context: Context) {
        val defaultPreferenceName = context.packageName + "_preferences"
        sharedPreferences = context.getSharedPreferences(defaultPreferenceName, Context.MODE_PRIVATE)
    }

    fun getToken(): String {
        return sharedPreferences.getString(PREF_TOKEN, "") ?: ""
    }

    fun setToken(value: String) {
        sharedPreferences.edit().putString(PREF_TOKEN, value).apply()
    }

    fun getFirebaseToken(): String {
        return sharedPreferences.getString(PREF_FIREBASE_TOKEN, "")?:""
    }

    fun setFirebaseToken(value: String) {
        sharedPreferences.edit().putString(PREF_FIREBASE_TOKEN, value).apply()
    }


    fun getTokenOnWaitlist(): String {
        return sharedPreferences.getString(TOKEN_ON_WAITLIST, "") ?: ""
    }

    fun setTokenOnWaitlist(value: String) {
        sharedPreferences.edit().putString(TOKEN_ON_WAITLIST, value).apply()
    }

    fun getUsername(): String {
        return sharedPreferences.getString(PREF_USERNAME, "") ?: ""
    }

    fun setUsername(value: String) {
        sharedPreferences.edit().putString(PREF_USERNAME, value).apply()
    }

    fun clearPreferences() {
        sharedPreferences.edit { clear() }
    }


    fun getUserId(): Int? {
        return sharedPreferences.getInt("user_id", 0).takeIf { it != 0 }
    }

}