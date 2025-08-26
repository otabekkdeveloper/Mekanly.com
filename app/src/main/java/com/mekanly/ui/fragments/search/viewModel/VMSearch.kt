package com.mekanly.ui.fragments.search.viewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import com.mekanly.data.models.House
import com.mekanly.data.models.HouseCategory
import com.mekanly.data.models.Location
import com.mekanly.data.models.Option
import com.mekanly.data.models.PriceRange
import com.mekanly.data.repository.HousesRepository.Companion.LIMIT_REGULAR
import com.mekanly.data.request.FilterBody
import com.mekanly.data.request.ReactionBody
import com.mekanly.domain.model.ResponseBodyState
import com.mekanly.domain.useCase.GetPaginatedHousesUseCase
import com.mekanly.domain.useCase.SearchPaginatedHousesUseCase
import com.mekanly.domain.useCase.ToggleFavoritesUseCase
import com.mekanly.utils.Constants.Companion.SORT_BY_CREATED_AT
import com.mekanly.utils.Constants.Companion.SORT_ORDER_DESC
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class VMSearch() : ViewModel() {

    private val _houses = MutableStateFlow<MutableList<House>>(mutableListOf())
    val houses: StateFlow<List<House>> = _houses.asStateFlow()

    private val _searchState = MutableStateFlow<ResponseBodyState>(ResponseBodyState.Initial)
    val searchState: StateFlow<ResponseBodyState> = _searchState.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    private val _needToReinitialiseAdapter = MutableStateFlow(false)

    private val getHousesUseCase by lazy { GetPaginatedHousesUseCase() }
    private val searchUseCase by lazy { SearchPaginatedHousesUseCase() }
    private val toggleFavoritesUseCase by lazy { ToggleFavoritesUseCase() }

    private val _search = MutableStateFlow<String>("")
    val search: StateFlow<String> = _search

    private val _selectedLocation = MutableStateFlow<Location?>(null)
    val selectedLocation: StateFlow<Location?> = _selectedLocation

    private val _favoriteToggleState = MutableStateFlow<ResponseBodyState>(ResponseBodyState.Initial)
    val favoriteToggleState: StateFlow<ResponseBodyState> = _favoriteToggleState.asStateFlow()


    private val _selectedCategory = MutableStateFlow<HouseCategory?>(null)
    val selectedCategory: StateFlow<HouseCategory?> = _selectedCategory

    private val _selectedPropertyTypes = MutableStateFlow<List<Option>>(emptyList())
    val selectedPropertyTypes: StateFlow<List<Option>> = _selectedPropertyTypes

    private val _selectedRepairTypes = MutableStateFlow<List<Option>>(emptyList())
    val selectedRepairTypes: StateFlow<List<Option>> = _selectedRepairTypes

    private val _selectedOpportunities = MutableStateFlow<List<Option>>(emptyList())
    val selectedOpportunities: StateFlow<List<Option>> = _selectedOpportunities

    private val _owner = MutableStateFlow<String?>(null)
    val owner: StateFlow<String?> = _owner

    private val _image = MutableStateFlow<Boolean>(false)
    val image: StateFlow<Boolean> = _image

    private val _status = MutableStateFlow<String?>(null)
    val status: StateFlow<String?> = _status

    private val _sortOrder = MutableStateFlow<String>(SORT_ORDER_DESC)
    val sortOrder: StateFlow<String> = _sortOrder

    private val _sortBy = MutableStateFlow<String>(SORT_BY_CREATED_AT)
    val sortBy: StateFlow<String> = _sortBy

    private val _area = MutableStateFlow<PriceRange>(PriceRange(5, 700))
    val area: StateFlow<PriceRange> = _area

    private val _price = MutableStateFlow<PriceRange>(PriceRange())
    val price: StateFlow<PriceRange> = _price

    private val _roomNumber = MutableStateFlow<List<Int>>(emptyList())
    val roomNumber: StateFlow<List<Int>> = _roomNumber

    private val _floorNumber = MutableStateFlow<List<Int>>(emptyList())
    val floorNumber: StateFlow<List<Int>> = _floorNumber

    init {
//        getHouses()
    }

    fun getLoadingState(): Boolean {
        return _isLoading.value
    }

    fun needToReinitialise(): Boolean {
        return _needToReinitialiseAdapter.value
    }

    fun setReinitialiseFalse() {
        _needToReinitialiseAdapter.value = false
    }

    // ✅ Добавьте этот метод в ваш VMSearch класс

    fun updateHouseLikeStatus(houseId: Int, isLiked: Boolean) {
        val currentList = _houses.value.toMutableList()
        val houseIndex = currentList.indexOfFirst { it.id == houseId }

        if (houseIndex != -1) {
            currentList[houseIndex].favorite = isLiked
            _houses.value = currentList
        }
    }

    fun getHouses(
        offset: Int = 0
    ) {
        if (_isLoading.value) return
        Log.e("TAG", "getHouses: ")
        val filterBody: FilterBody = buildFilterBody()
        filterBody.offset = offset
        _isLoading.value = true
        getHousesUseCase.execute(filterBody) { result ->
            _searchState.value = result
            when (result) {
                is ResponseBodyState.SuccessList -> {
                    if (offset == 0) {
                        _houses.value.clear()
                        _needToReinitialiseAdapter.value = true
                    }
                    _isLoading.value = false
                    val data = result.dataResponse as List<House>
                    _houses.value.addAll(data)
                }

                is ResponseBodyState.Error -> {
                    _isLoading.value = false
                }

                else -> {
                    _isLoading.value = false
                }


            }
        }

    }


    fun searchHouses(offset: Int = 0) {
        val query = _search.value
        if (query.isBlank()) {
            getHouses(offset)
            return
        }

        _isLoading.value = true
        _searchState.value = ResponseBodyState.Loading

        searchUseCase.search(query, offset, LIMIT_REGULAR.toInt()) { result ->
            _searchState.value = result
            when (result) {
                is ResponseBodyState.SuccessList -> {
                    if (offset == 0) {
                        _houses.value.clear()
                        _needToReinitialiseAdapter.value = true
                    }
                    _isLoading.value = false

                    @Suppress("UNCHECKED_CAST") val data = result.dataResponse as List<House>
                    _houses.value.addAll(data)
                }

                is ResponseBodyState.Error -> {
                    _isLoading.value = false
                }

                else -> {
                    _isLoading.value = false
                }
            }
        }
    }

    /**
     * Функция для загрузки следующей страницы результатов поиска
     */
    fun loadMoreSearchResults() {
        val currentOffset = _houses.value.size
        if (!_isLoading.value) {
            if (_search.value.isNotBlank()) {
                searchHouses(currentOffset)
            } else {
                getHouses(currentOffset)
            }
        }
    }

    /**
     * Очистить строку поиска и результаты
     */
    fun clearSearch() {
        _search.value = ""
        _houses.value.clear()
        _needToReinitialiseAdapter.value = true
        getHouses()
    }


    fun setSelectedLocation(location: Location?) {
        if (_selectedLocation.value != location) {
            _selectedLocation.value = location
//            _houses.value.clear()
//            _needToReinitialiseAd apter.value = true
        }
    }


    fun setSelectedCategory(category: HouseCategory?) {
        if (_selectedCategory.value != category)
            _selectedCategory.value = category
//        _houses.value.clear()
//        _needToReinitialiseAdapter.value = true

    }

    fun setSelectedPropertyTypes(types: List<Option>) {
        _selectedPropertyTypes.value = types
        _houses.value.clear()
        _needToReinitialiseAdapter.value = true
    }

    fun setSelectedRepairTypes(types: List<Option>) {
        _selectedRepairTypes.value = types
    }

    fun setSelectedOpportunities(opps: List<Option>) {
        _selectedOpportunities.value = opps
    }

    fun setOwner(owner: String?) {
        if (_owner.value != owner) {
            _owner.value = owner
            _houses.value.clear()
            _needToReinitialiseAdapter.value = true
        }
    }

    fun setImage(hasImage: Boolean) {
        _image.value = hasImage
    }

    fun setSearch(search: String) {
        _search.value = search
    }

    fun setSortOrder(order: String) {
        _sortOrder.value = order
    }

    fun setSortBy(sortBy: String) {
        _sortBy.value = sortBy
    }

    fun setArea(area: PriceRange) {
        _area.value = area
    }

    fun setPrice(price: PriceRange) {
        if (_price.value != price) {
            _price.value = price
//            _houses.value.clear()
//            _needToReinitialiseAdapter.value = true
        }
    }


    fun setRoomNumber(room: List<Int>) {
        if (_roomNumber.value != room) {
            _roomNumber.value = room
            _houses.value.clear()
            _needToReinitialiseAdapter.value = true
        }
    }

    fun setFloorNumber(floor: List<Int>) {
        if (_floorNumber.value != floor) {
            _floorNumber.value = floor
            _houses.value.clear()
            _needToReinitialiseAdapter.value = true
        }
    }

    fun clearAllFilters() {
        _selectedLocation.value = null
        _selectedCategory.value = null
        _price.value = PriceRange()
        _selectedPropertyTypes.value = emptyList()
        _selectedRepairTypes.value = emptyList()
        _selectedOpportunities.value = emptyList()
        _owner.value = null
        _image.value = false
        _status.value = null
        _sortOrder.value = SORT_ORDER_DESC
        _sortBy.value = SORT_BY_CREATED_AT
        _area.value = PriceRange()
        _roomNumber.value = emptyList()
        _floorNumber.value = emptyList()
    }


    fun toggleFavorite(house: House) {
        val reactionBody = ReactionBody(
            id = house.id,
            type = "shop"
        )

        _favoriteToggleState.value = ResponseBodyState.Loading

        toggleFavoritesUseCase.execute(reactionBody) { result ->
            _favoriteToggleState.value = result

            when (result) {
                is ResponseBodyState.Success -> {
                    // Обновляем локальное состояние дома
                    updateHouseFavoriteStatus(house.id, !house.liked)
                }
                is ResponseBodyState.Error -> {
                    // Обработка ошибки - можно добавить логирование или показать ошибку пользователю
                    Log.e("VMSearch", "Error toggling favorite: ${result.error}")
                }
                else -> {}
            }
        }}

    private fun updateHouseFavoriteStatus(houseId: Int, isLiked: Boolean) {
        val currentHouses = _houses.value.toMutableList()
        val houseIndex = currentHouses.indexOfFirst { it.id == houseId }

        if (houseIndex != -1) {
            val updatedHouse = currentHouses[houseIndex].copy(liked = isLiked)
            currentHouses[houseIndex] = updatedHouse
            _houses.value = currentHouses
        }
    }

    fun toggleFavoriteById(houseId: Int) {
        val house = _houses.value.find { it.id == houseId }
        house?.let { toggleFavorite(it) }
    }




    private fun buildFilterBody(): FilterBody {
        return FilterBody(
            categories = _selectedCategory.value?.id?.let { "[$it]" },
            location = _selectedLocation.value?.id?.let { "[$it]" },
            possibilities = _selectedOpportunities.value.takeIf { it.isNotEmpty() }
                ?.joinToString(prefix = "[", postfix = "]", separator = ",") { it.id.toString() },
            image = _image.value.takeIf { it },
            who = _owner.value,
            minPrice = _price.value.min,
            maxPrice = _price.value.max,
            minArea = _area.value.min,
            maxArea = _area.value.max,
            propertyType = _selectedPropertyTypes.value.takeIf { it.isNotEmpty() }
                ?.joinToString(prefix = "[", postfix = "]", separator = ",") { it.id.toString() },
            repairType = _selectedRepairTypes.value.takeIf { it.isNotEmpty() }
                ?.joinToString(prefix = "[", postfix = "]", separator = ",") { it.id.toString() },
            status = _status.value,
            roomNumber = _roomNumber.value.takeIf { it.isNotEmpty() }
                ?.joinToString(prefix = "[", postfix = "]", separator = ","),
            floorNumber = _floorNumber.value.takeIf { it.isNotEmpty() }
                ?.joinToString(prefix = "[", postfix = "]", separator = ","),
            sortBy = _sortBy.value,
            sortOrder = _sortOrder.value,
            limit = LIMIT_REGULAR.toInt(),
            offset = 0)
    }


}
