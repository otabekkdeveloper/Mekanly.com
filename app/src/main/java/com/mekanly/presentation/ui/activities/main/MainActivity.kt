package com.mekanly.presentation.ui.activities.main

import com.mekanly.presentation.ui.adapters.pagerAdapters.AdapterViewPager
import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.mekanly.LanguageManager
import com.mekanly.PreferencesHelper
import com.mekanly.R
import com.mekanly.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var adapterViewPager: AdapterViewPager
    private lateinit var preferencesHelper: PreferencesHelper





    @SuppressLint("ResourceType")
    override fun onCreate(savedInstanceState: Bundle?) {
        preferencesHelper = PreferencesHelper(this)

        // Установить язык
        LanguageManager.setLocale(this, preferencesHelper.getLanguage())

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

    }


}









