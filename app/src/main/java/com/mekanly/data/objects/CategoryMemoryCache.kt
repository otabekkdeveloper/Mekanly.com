import com.mekanly.data.dataModels.DataBusinessProfileCategory

object CategoryMemoryCache {
    private var categories: List<DataBusinessProfileCategory>? = null
    private var cacheTimeMillis: Long = 0

    private const val CACHE_TIMEOUT = 7 * 24 * 60 * 60 * 1000L // 7 дней в миллисекундах

    fun saveCategories(data: List<DataBusinessProfileCategory>) {
        categories = data
        cacheTimeMillis = System.currentTimeMillis()
    }

    fun getCategories(): List<DataBusinessProfileCategory>? {
        return if (isCacheValid()) categories else null
    }

    fun hasCategories(): Boolean {
        return categories != null && isCacheValid()
    }

    private fun isCacheValid(): Boolean {
        val currentTime = System.currentTimeMillis()
        return (currentTime - cacheTimeMillis) < CACHE_TIMEOUT
    }

    fun clearCache() {
        categories = null
        cacheTimeMillis = 0
    }
}
