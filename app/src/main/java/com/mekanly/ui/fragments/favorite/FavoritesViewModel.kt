package com.mekanly.ui.fragments.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mekanly.data.local.preferences.AppPreferences
import com.mekanly.data.models.FavoritesRequest
import com.mekanly.data.models.House
import com.mekanly.data.models.ShopProduct
import com.mekanly.data.repository.HousesRepository.Companion.LIMIT_REGULAR
import com.mekanly.data.request.ReactionBody
import com.mekanly.domain.model.ResponseBodyState
import com.mekanly.domain.useCase.GetFavoriteHouseUseCase
import com.mekanly.domain.useCase.ToggleFavoritesUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class FavoritesViewModel : ViewModel() {

    private val _uiState = MutableStateFlow<ResponseBodyState>(ResponseBodyState.Loading)
    val uiState: StateFlow<ResponseBodyState> = _uiState.asStateFlow()

    private val _houses = MutableStateFlow<List<House>>(emptyList())
    val houses: StateFlow<List<House>> = _houses.asStateFlow()

    private val _products = MutableStateFlow<List<ShopProduct>>(emptyList())
    val products: StateFlow<List<ShopProduct>> = _products.asStateFlow()

    private val toggleFavoritesUseCase by lazy { ToggleFavoritesUseCase() }
    private val getFavoriteHouseUseCase by lazy { GetFavoriteHouseUseCase() }
    private val getFavoriteShopProductUseCase by lazy { GetFavoriteHouseUseCase() }

    // ✅ УБИРАЕМ отдельные списки лайков, используем поле favorite в моделях
    private var housesLoaded = false
    private var productsLoaded = false

    fun getFavorites() {
        val token = AppPreferences.getToken()

        if (token.isNullOrEmpty()) {
            _uiState.value = ResponseBodyState.Error("Token is missing")
            return
        }

        _uiState.value = ResponseBodyState.Loading

        housesLoaded = false
        productsLoaded = false

        loadFavoriteHouses()
        loadFavoriteProducts()
    }

    private fun loadFavoriteHouses() {
        viewModelScope.launch {
            val houseRequest = FavoritesRequest(
                type = "House",
                category_id = null,
                limit = LIMIT_REGULAR.toInt(),
                offset = 0
            )

            getFavoriteHouseUseCase.execute(houseRequest) { result ->
                when (result) {
                    is ResponseBodyState.SuccessList -> {
                        val housesList = result.dataResponse as? List<House> ?: emptyList()
                        // ✅ ИСПРАВЛЕНО: Устанавливаем favorite = true для всех загруженных домов
                        housesList.forEach { it.favorite = true }
                        _houses.value = housesList

                        housesLoaded = true
                        checkLoadingComplete()
                    }
                    is ResponseBodyState.Error -> {
                        housesLoaded = true
                        if (productsLoaded) {
                            _uiState.value = result
                        }
                    }
                    else -> {}
                }
            }
        }
    }

    private fun loadFavoriteProducts() {
        viewModelScope.launch {
            val productRequest = FavoritesRequest(
                type = "ShopProduct",
                category_id = null,
                limit = LIMIT_REGULAR.toInt(),
                offset = 0
            )

            getFavoriteShopProductUseCase.invoke(productRequest) { result ->
                when (result) {
                    is ResponseBodyState.SuccessList -> {
                        try {
                            val productsList = result.dataResponse as? List<ShopProduct> ?: emptyList()
                            // ✅ ИСПРАВЛЕНО: Устанавливаем favorite = true для всех загруженных продуктов
                            productsList.forEach { it.favorited = true }
                            _products.value = productsList

                            productsLoaded = true
                            checkLoadingComplete()
                        } catch (e: Exception) {
                            productsLoaded = true
                            _uiState.value = ResponseBodyState.Error("Ошибка парсинга данных: ${e.message}")
                        }
                    }
                    is ResponseBodyState.Error -> {
                        productsLoaded = true
                        if (housesLoaded) {
                            _uiState.value = result
                        } else {
                            checkLoadingComplete()
                        }
                    }
                    else -> {}
                }
            }
        }
    }

    // ✅ ИСПРАВЛЕНО: Используем поле favorite из модели
    fun isLiked(itemId: Int, type: String = "House"): Boolean {
        return when (type) {
            "House" -> {
                _houses.value.find { it.id == itemId }?.favorite ?: false
            }
            "ShopProduct" -> {
                _products.value.find { it.id == itemId }?.favorited ?: false
            }
            else -> false
        }
    }

    // ✅ Для совместимости со старым кодом
    fun isHouseLiked(houseId: Int): Boolean {
        return _houses.value.find { it.id == houseId }?.favorite ?: false
    }

    // ✅ ИСПРАВЛЕНО: Обновляем поле favorite в модели
    fun toggleLike(itemId: Int, currentLikeStatus: Boolean, type: String = "House") {
        val token = AppPreferences.getToken()
        if (token.isNullOrEmpty()) return

        viewModelScope.launch {
            val request = ReactionBody(
                id = itemId,
                type = type
            )

            // ✅ Оптимистичное обновление - обновляем поле favorite в модели
            when (type) {
                "House" -> {
                    val updatedHouses = _houses.value.map { house ->
                        if (house.id == itemId) {
                            house.copy().apply { favorite = !currentLikeStatus }
                        } else house
                    }
                    _houses.value = updatedHouses
                }
                "ShopProduct" -> {
                    val updatedProducts = _products.value.map { product ->
                        if (product.id == itemId) {
                            product.copy().apply { favorited = !currentLikeStatus }
                        } else product
                    }
                    _products.value = updatedProducts
                }
            }

            toggleFavoritesUseCase.execute(request) { result ->
                if (result is ResponseBodyState.Error) {
                    // ✅ Откат на случай ошибки
                    when (type) {
                        "House" -> {
                            val rolledBackHouses = _houses.value.map { house ->
                                if (house.id == itemId) {
                                    house.copy().apply { favorite = currentLikeStatus }
                                } else house
                            }
                            _houses.value = rolledBackHouses
                        }
                        "ShopProduct" -> {
                            val rolledBackProducts = _products.value.map { product ->
                                if (product.id == itemId) {
                                    product.copy().apply { favorited = currentLikeStatus }
                                } else product
                            }
                            _products.value = rolledBackProducts
                        }
                    }
                }
            }
        }
    }

    private fun checkLoadingComplete() {
        if (housesLoaded && productsLoaded) {
            _uiState.value = ResponseBodyState.SuccessList(emptyList<Any>())
        }
    }

    fun refreshHouses() {
        housesLoaded = false
        loadFavoriteHouses()
    }

    fun refreshProducts() {
        productsLoaded = false
        loadFavoriteProducts()
    }

    fun isDataLoaded(): Boolean {
        return housesLoaded && productsLoaded
    }

    // ✅ ДОБАВЛЕНО: Функция для обновления состояния лайка конкретного элемента (для использования в других адаптерах)
    fun updateItemLikeStatus(itemId: Int, isLiked: Boolean, type: String = "House") {
        when (type) {
            "House" -> {
                val updatedHouses = _houses.value.map { house ->
                    if (house.id == itemId) {
                        house.copy().apply { favorite = isLiked }
                    } else house
                }
                _houses.value = updatedHouses
            }
            "ShopProduct" -> {
                val updatedProducts = _products.value.map { product ->
                    if (product.id == itemId) {
                        product.copy().apply { favorited = isLiked }
                    } else product
                }
                _products.value = updatedProducts
            }
        }
    }
}