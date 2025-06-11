package com.mekanly.ui.fragments.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mekanly.data.local.preferences.AppPreferences
import com.mekanly.data.models.FavoritesRequest
import com.mekanly.data.models.House
import com.mekanly.data.models.ShopProduct
import com.mekanly.data.repository.HousesRepository.Companion.LIMIT_REGULAR
import com.mekanly.domain.model.ResponseBodyState
import com.mekanly.domain.useCase.GetFavoriteHouseUseCase
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

    private val getFavoriteHouseUseCase by lazy { GetFavoriteHouseUseCase() }
    private val getFavoriteShopProductUseCase by lazy { GetFavoriteHouseUseCase() }

    fun getFavorites() {
        val token = AppPreferences.getToken()

        if (token.isNullOrEmpty()) {
            _uiState.value = ResponseBodyState.Error("Token is missing")
            return
        }

        _uiState.value = ResponseBodyState.Loading

        // Загружаем оба типа данных параллельно
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
                        _houses.value = result.dataResponse as? List<House> ?: emptyList()
                        checkLoadingComplete()
                    }
                    is ResponseBodyState.Error -> {
                        _uiState.value = result
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
                            _products.value = result.dataResponse as? List<ShopProduct> ?: emptyList()
                            checkLoadingComplete()
                        } catch (e: Exception) {
                            // Обработка ошибки парсинга
                            _uiState.value = ResponseBodyState.Error("Ошибка парсинга данных: ${e.message}")
                        }
                    }
                    is ResponseBodyState.Error -> {
                        _uiState.value = result
                    }
                    else -> {}
                }
            }
        }
    }

    private fun checkLoadingComplete() {
        // Устанавливаем состояние успеха, когда данные загружены
        // (можно добавить дополнительную логику для отслеживания завершения обеих загрузок)
        _uiState.value = ResponseBodyState.SuccessList(emptyList<Any>())
    }

    // Функции для обновления конкретного типа данных
    fun refreshHouses() {
        loadFavoriteHouses()
    }

    fun refreshProducts() {
        loadFavoriteProducts()
    }
}