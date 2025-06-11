package com.mekanly.presentation.ui.fragments.businessProfile.viewModel

import androidx.lifecycle.ViewModel
import com.mekanly.data.models.BusinessProfile
import com.mekanly.data.models.BusinessCategory
import com.mekanly.data.repository.HousesRepository.Companion.LIMIT_REGULAR
import com.mekanly.domain.model.ResponseBodyState
import com.mekanly.domain.useCase.GetBusinessCategoriesUseCase
import com.mekanly.domain.useCase.GetBusinessProfilesUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
sealed class FragmentBusinessProfileState(){
    data class SuccessBusinessProfiles(val dataResponse:List<BusinessProfile>):FragmentBusinessProfileState()
    data class SuccessCategories(val dataResponse:List<BusinessCategory>):FragmentBusinessProfileState()
    data object Loading:FragmentBusinessProfileState()
    data object Initial:FragmentBusinessProfileState()
    data class Error(val error:Any):FragmentBusinessProfileState()
}
class VMBusinessProfiles : ViewModel() {

    private val _fragmentState = MutableStateFlow<FragmentBusinessProfileState>(FragmentBusinessProfileState.Initial)
    val fragmentState: StateFlow<FragmentBusinessProfileState> = _fragmentState.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    private val _isLoadingMore = MutableStateFlow(false) // Отдельный флаг для пагинации

    // Используй List вместо MutableList для thread safety
    private val _businessProfiles = MutableStateFlow<List<BusinessProfile>>(emptyList())
    val businessProfiles: StateFlow<List<BusinessProfile>> = _businessProfiles.asStateFlow()



    private val useCase by lazy { GetBusinessCategoriesUseCase() }
    private val getBusinessProfilesUseCase by lazy { GetBusinessProfilesUseCase() }

    // Добавь переменные для контроля пагинации
    private var currentPage = 0
    private var hasMoreData = true
    private var isFirstLoad = true

    init {
        getBusinessProfileCategories()
        getPageInfoDefault(0)
    }

    private fun getBusinessProfileCategories() {
        useCase.execute {
            when (it) {
                is ResponseBodyState.Error -> {
                    _fragmentState.value = FragmentBusinessProfileState.Error(it.error)
                }
                ResponseBodyState.Loading -> {
                    if (isFirstLoad) {
                        _fragmentState.value = FragmentBusinessProfileState.Loading
                    }
                }
                is ResponseBodyState.SuccessList -> {
                    _fragmentState.value = FragmentBusinessProfileState.SuccessCategories(
                        it.dataResponse as List<BusinessCategory>
                    )
                }
                else -> {}
            }
        }
    }

    fun getLoadingState(): Boolean {
        return _isLoading.value || _isLoadingMore.value
    }

    fun getPageInfoDefault(offset: Int) {
        // Предотврати множественные вызовы
        if (_isLoading.value || _isLoadingMore.value || !hasMoreData) {
            return
        }

        // Устанавливай правильные флаги
        if (offset == 0) {
            _isLoading.value = true
            isFirstLoad = true
            currentPage = 0
            hasMoreData = true
        } else {
            _isLoadingMore.value = true
        }

        _fragmentState.value = FragmentBusinessProfileState.Loading

        getBusinessProfilesUseCase.invoke(offset.toLong()) { result ->
            when (result) {
                is ResponseBodyState.SuccessList -> {
                    val newData = result.dataResponse as List<BusinessProfile>

                    // Проверь, есть ли еще данные
                    hasMoreData = newData.size >= LIMIT_REGULAR

                    // Обнови список thread-safe способом
                    val updatedList = if (offset == 0) {
                        newData // Первая загрузка - заменяй весь список
                    } else {
                        _businessProfiles.value + newData // Пагинация - добавляй к существующему
                    }

                    _businessProfiles.value = updatedList
                    currentPage++

                    // Сбрось флаги загрузки
                    _isLoading.value = false
                    _isLoadingMore.value = false
                    isFirstLoad = false

                    _fragmentState.value = FragmentBusinessProfileState.SuccessBusinessProfiles(updatedList)
                }
                is ResponseBodyState.Error -> {
                    _isLoading.value = false
                    _isLoadingMore.value = false
                    _fragmentState.value = FragmentBusinessProfileState.Error(result.error)
                }
                is ResponseBodyState.Loading -> {
                    // Loading state уже установлен выше
                }
                else -> {
                    _isLoading.value = false
                    _isLoadingMore.value = false
                }
            }
        }
    }

    // Добавь метод для refresh
    fun refreshData() {
        _businessProfiles.value = emptyList()
        hasMoreData = true
        currentPage = 0
        getPageInfoDefault(0)
    }

    fun setInitialState() {
        _fragmentState.value = FragmentBusinessProfileState.Initial
    }

    // Добавь метод для проверки, можно ли загружать еще
    fun canLoadMore(): Boolean {
        return hasMoreData && !getLoadingState()
    }
}