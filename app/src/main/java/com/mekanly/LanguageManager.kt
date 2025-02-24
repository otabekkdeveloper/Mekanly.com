package com.mekanly

// LanguageManager.kt
import android.content.Context
import android.content.res.Configuration
import java.util.Locale

object LanguageManager {

    fun setLocale(context: Context, languageCode: String) {
        val locale = Locale(languageCode)
        Locale.setDefault(locale)

        val config = Configuration()
        config.setLocale(locale)

        // Обновляем конфигурацию для ресурсов приложения
        context.resources.updateConfiguration(config, context.resources.displayMetrics)
    }
}
