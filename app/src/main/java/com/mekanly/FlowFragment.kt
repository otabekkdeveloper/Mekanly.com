package com.mekanly

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.ViewPager2
import com.mekanly.adapters.ImageAdapter
import com.mekanly.adapters.PropertyAdapter
import com.mekanly.databinding.FragmentFlowBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.security.cert.X509Certificate
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager
import java.util.*

class FlowFragment : Fragment() {
    private lateinit var binding: FragmentFlowBinding
    private lateinit var propertyAdapter: PropertyAdapter
    private val bannerHandler = Handler(Looper.getMainLooper())
    private val bannerImages = listOf(
        R.drawable.image1,
        R.drawable.image1,
        R.drawable.image1
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentFlowBinding.inflate(inflater, container, false)

        // Настройка ViewPager2
        setupViewPager()

        // Настройка RecyclerView
        setupRecyclerView()

        return binding.root
    }

    private fun setupViewPager() {
        val bannerAdapter = ImageAdapter(bannerImages)
        binding.bannerViewPager.adapter = bannerAdapter

        // Автоматическое пролистывание
        val runnable = object : Runnable {
            override fun run() {
                val nextItem = (binding.bannerViewPager.currentItem + 1) % bannerImages.size
                binding.bannerViewPager.setCurrentItem(nextItem, true)
                bannerHandler.postDelayed(this, 2000) // Интервал 2 секунды
            }
        }
        bannerHandler.postDelayed(runnable, 2000)

        // Очистка handler при уничтожении
        binding.bannerViewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                bannerHandler.removeCallbacksAndMessages(null)
                bannerHandler.postDelayed(runnable, 2000)
            }
        })
    }

    private fun setupRecyclerView() {
        propertyAdapter = PropertyAdapter(emptyList())
        binding.recyclerViewTwo.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = propertyAdapter
        }
        // Здесь можно вызвать функцию для загрузки данных в RecyclerView
    }

    override fun onDestroyView() {
        super.onDestroyView()
        bannerHandler.removeCallbacksAndMessages(null)
    }
}
