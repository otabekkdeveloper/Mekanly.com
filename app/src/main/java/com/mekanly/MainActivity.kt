package com.mekanly

import ViewPagerAdapter
import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayoutMediator
import com.mekanly.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var viewPagerAdapter: ViewPagerAdapter
    @SuppressLint("ResourceType")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
//        window.statusBarColor = ContextCompat.getColor(this, R.drawable.btn_bg_color)


        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        window.statusBarColor = Color.TRANSPARENT

        window.setBackgroundDrawableResource(R.drawable.btn_bg_color)



        // Настраиваем ViewPager2 с адаптером
        viewPagerAdapter = ViewPagerAdapter(this)
        binding.viewPager.adapter = viewPagerAdapter

        // Связываем TabLayout с ViewPager2 и добавляем иконки
        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            when (position) {
                0 -> {
                    tab.text = "Baş sahypa"
                    tab.setIcon(R.drawable.house_logo) // Устанавливаем иконку для первой вкладки
                }
                1 -> {
                    tab.text = "Emläkler"
                    tab.setIcon(R.drawable.search)
                }
                2 -> {
                    tab.text = "Satyjylar"
                    tab.setIcon(R.drawable.shop_svgrepo_com)
                }
                3 -> {
                    tab.text = "Halanlarym"
                    tab.setIcon(R.drawable.heart_icon)
                }
                4 -> {
                    tab.text = "Menýu"
                    tab.setIcon(R.drawable.menu_ic)
                }
            }
        }.attach()


        // Изменяем иконку для выбранного фрагмента
        binding.tabLayout.addOnTabSelectedListener(object : com.google.android.material.tabs.TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: com.google.android.material.tabs.TabLayout.Tab) {
                when (tab.position) {
                    0 -> tab.setIcon(R.drawable.house)  // Иконка для выбранной вкладки
                    1 -> tab.setIcon(R.drawable.emlak_bold)
                    2 -> tab.setIcon(R.drawable.shopping_bold)
                    3 -> tab.setIcon(R.drawable.heart_bold)
                    4 -> tab.setIcon(R.drawable.menu_ic)
                }
            }

            override fun onTabUnselected(tab: com.google.android.material.tabs.TabLayout.Tab) {
                when (tab.position) {
                    0 -> tab.setIcon(R.drawable.house_logo)  // Возвращаем обычную иконку
                    1 -> tab.setIcon(R.drawable.search)
                    2 -> tab.setIcon(R.drawable.shop_svgrepo_com)
                    3 -> tab.setIcon(R.drawable.heart_icon)
                    4 -> tab.setIcon(R.drawable.menu_ic)
                }
            }

            override fun onTabReselected(tab: com.google.android.material.tabs.TabLayout.Tab) {
                // Можно обработать повторное нажатие на уже выбранную вкладку
            }
        })




        // Отключаем свайпы
        disableSwipe(binding.viewPager)
    }

    private fun disableSwipe(viewPager: ViewPager2) {
        viewPager.isUserInputEnabled = false // Полное отключение свайпов



    }
}







