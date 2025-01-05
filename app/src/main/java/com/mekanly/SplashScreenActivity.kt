package com.mekanly

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.mekanly.databinding.ActivitySplashScreenBinding
import com.mekanly.presentation.ui.activities.main.MainActivity
import pl.droidsonroids.gif.GifDrawable

class SplashScreenActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySplashScreenBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        window.statusBarColor = ContextCompat.getColor(this, R.color.background)

        // Создаем GifDrawable
        val gifDrawable = GifDrawable(resources, R.raw.gif_two)

        // Устанавливаем GIF в ImageView
        binding.imageViewGif.setImageDrawable(gifDrawable)

        // Устанавливаем, чтобы GIF воспроизводился только один раз
        gifDrawable.loopCount = 1

        // Получаем длительность GIF-анимации
        val gifDuration = gifDrawable.duration.toLong()

        // Переход на главный экран после завершения GIF
        binding.imageViewGif.postDelayed({
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }, gifDuration)
    }
}
