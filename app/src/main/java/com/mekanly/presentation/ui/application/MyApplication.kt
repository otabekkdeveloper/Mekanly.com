package com.mekanly.presentation.ui.application

import android.app.Application
import okhttp3.Cache
import java.io.File

class MyApplication : Application() {


    override fun onCreate() {
        super.onCreate()
        CacheManager.initialiseCache(this)
    }


    object CacheManager {
        private lateinit var cache: Cache
        fun initialiseCache(application: Application) {
            val downloadDirectory = File(application.applicationContext.cacheDir, "http_cache")
            cache = Cache(downloadDirectory, maxSize = 50 * 1024 * 1000)
        }

        fun getCache(): Cache {
            return cache
        }
    }
}