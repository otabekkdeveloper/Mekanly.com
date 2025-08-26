package com.mekanly.helpers

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.mekanly.data.models.DataGlobalOptions

object PreferencesHelper {
    private lateinit var  sharedPreferences: SharedPreferences
    private val gson = Gson()


    fun initialize(context: Context) {
        sharedPreferences = getSharedPreferences(context)
    }

    fun setLanguage(languageCode: String) {
        sharedPreferences.edit().putString("language", languageCode).apply()
    }

    fun getLanguage(): String {
        return sharedPreferences.getString("language", "en") ?: "en"
    }

    fun saveGlobalOptions(data: DataGlobalOptions) {
        val json = gson.toJson(data)
        sharedPreferences.edit().putString("global_options", json).apply()
    }

    fun getGlobalOptions(): DataGlobalOptions? {
        val json = sharedPreferences.getString("global_options", null) ?: return null
        return gson.fromJson(json, DataGlobalOptions::class.java)
    }

    private fun getSharedPreferences(context: Context): SharedPreferences {
        return  context.getSharedPreferences("app_preferences", Context.MODE_PRIVATE)
    }
}
