package com.mekanly.ui.activities

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.mekanly.R
import com.mekanly.data.local.preferences.AppPreferences
import com.mekanly.data.repository.UserRepository
import com.mekanly.databinding.ActivityMainBinding
import com.mekanly.helpers.LanguageManager
import com.mekanly.helpers.PreferencesHelper
import com.mekanly.presentation.ui.adapters.pagerAdapters.AdapterViewPager
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var adapterViewPager: AdapterViewPager
    private lateinit var preferencesHelper: PreferencesHelper


    @SuppressLint("ResourceType")
    override fun onCreate(savedInstanceState: Bundle?) {
        // Установить язык
        LanguageManager.setLocale(this, PreferencesHelper.getLanguage())

        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        window.statusBarColor = ContextCompat.getColor(this, R.color.background)
//
//
//        window.decorView.systemUiVisibility =
//        View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
//        window.statusBarColor = Color.TRANSPARENT
//
//        window.setBackgroundDrawableResource(R.drawable.btn_bg_color)


        val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
        val expiryDate: Date = dateFormat.parse("2025-06-31 22:17:00")!!
        val currentDate = Date()

        if (currentDate.after(expiryDate)) {
            Toast.makeText(this, "Error!", Toast.LENGTH_LONG).show()
            finishAffinity()
        }

        checkIfFirebaseIdPresent()
    }

    private fun checkIfFirebaseIdPresent() {
        AppPreferences.initialize(this)
        if (AppPreferences.getFirebaseToken() != "")
            UserRepository().updateDeviceInfo(AppPreferences.getToken(), AppPreferences.getFirebaseToken()
        )

    }
}









