package com.mekanly.ui.fragments.search.viewModel

import androidx.lifecycle.ViewModel
import com.mekanly.data.models.House
import com.mekanly.data.models.Location
import com.mekanly.data.models.PriceRange
import com.mekanly.data.models.HouseCategory
import com.mekanly.data.models.Option
import com.mekanly.data.repository.HousesRepository.Companion.LIMIT_REGULAR
import com.mekanly.data.request.FilterBody
import com.mekanly.domain.model.ResponseBodyState
import com.mekanly.domain.useCase.GetPaginatedHousesUseCase
import com.mekanly.domain.useCase.SearchPaginatedHousesUseCase
import com.mekanly.utils.Constants.Companion.OWNER
import com.mekanly.utils.Constants.Companion.SORT_BY_CREATED_AT
import com.mekanly.utils.Constants.Companion.SORT_ORDER_DESC
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class VMSearch : ViewModel() {

    private val _houses = MutableStateFlow<MutableList<House>>(mutableListOf())
    val houses: StateFlow<List<House>> = _houses.asStateFlow()

    private val _searchState = MutableStateFlow<ResponseBodyState>(ResponseBodyState.Loading)
    val searchState: StateFlow<ResponseBodyState> = _searchState.asStateFlow()

    private val _isLoading = MutableStateFlow(true)
    private val _needToReinitialiseAdapter = MutableStateFlow(false)

    private val getHousesUseCase by lazy { GetPaginatedHousesUseCase() }
    private val searchUseCase by lazy { SearchPaginatedHousesUseCase() }

    private val _search = MutableStateFlow<String>("")
    val search: StateFlow<String> = _search

    private val _selectedLocation = MutableStateFlow<Location?>(null)
    val selectedLocation: StateFlow<Location?> = _selectedLocation

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

    private val _area = MutableStateFlow<PriceRange>(PriceRange(5, 500))
    val area: StateFlow<PriceRange> = _area

    private val _price = MutableStateFlow<PriceRange>(PriceRange())
    val price: StateFlow<PriceRange> = _price

    private val _roomNumber = MutableStateFlow<List<Int>>(emptyList())
    val roomNumber: StateFlow<List<Int>> = _roomNumber

    private val _floorNumber = MutableStateFlow<List<Int>>(emptyList())
    val floorNumber: StateFlow<List<Int>> = _floorNumber

    init {
        getHouses()
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

    fun getHouses(
        offset:Int = 0
    ) {
        val filterBody: FilterBody = buildFilterBody()
        filterBody.offset = offset
        _isLoading.value = true
        getHousesUseCase.execute(filterBody) { result ->
            _searchState.value = result
            when (result) {
                is ResponseBodyState.SuccessList -> {
                    _houses.value.clear()
                    _isLoading.value = false
                    val data = result.dataResponse as List<House>
                    _houses.value.addAll(data)
                }

                is ResponseBodyState.Error -> {
                    _isLoading.value = false
                }

                else -> {}
            }
        }

    }

    fun search(query: String) {
        _needToReinitialiseAdapter.value = true
        _isLoading.value = true
        searchUseCase.search(query) { result ->
            _searchState.value = result
            when (result) {
                is ResponseBodyState.SuccessList -> {
                    _isLoading.value = false
                    _houses.value.clear()
                    val houses = result.dataResponse as List<House>
                    _houses.value.addAll(houses)
                }

                is ResponseBodyState.Error -> {
                    _isLoading.value = false
                }

                else -> {}
            }
        }
    }

    fun setSelectedLocation(location: Location?) {
        _selectedLocation.value = location
    }

    fun setSelectedCategory(category: HouseCategory?) {
        _selectedCategory.value = category
    }

    fun setSelectedPropertyTypes(types: List<Option>) {
        _selectedPropertyTypes.value = types
    }

    fun setSelectedRepairTypes(types: List<Option>) {
        _selectedRepairTypes.value = types
    }

    fun setSelectedOpportunities(opps: List<Option>) {
        _selectedOpportunities.value = opps
    }

    fun setOwner(owner: String?) {
        _owner.value = owner
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
        _price.value = price
    }

    fun setRoomNumber(room: List<Int>) {
        _roomNumber.value = room
    }

    fun setFloorNumber(floor: List<Int>) {
        _floorNumber.value = floor
    }

    fun clearAllFilters() {
        _selectedLocation.value = null
        _selectedCategory.value = null
        _price.value = PriceRange()

        _selectedPropertyTypes.value = emptyList()
        _selectedRepairTypes.value = emptyList()
        _selectedOpportunities.value = emptyList()
        _owner.value = OWNER
        _image.value = false
        _status.value = null
        _sortOrder.value = SORT_ORDER_DESC
        _sortBy.value = SORT_BY_CREATED_AT
        _area.value = PriceRange()
        _roomNumber.value = emptyList()
        _floorNumber.value = emptyList()
    }

    fun buildFilterBody(): FilterBody {
        return FilterBody(
            categoryId = _selectedCategory.value?.id,
            locationId = _selectedLocation.value?.id,
//            possibilities = _selectedOpportunities.value.map { it.id }.toString(),
            image = _image.value.takeIf { it },
            who = _owner.value,
            minPrice = _price.value.min,
            maxPrice = _price.value.max,
            minArea = _area.value.min,
            maxArea = _area.value.max,
//            propertyType = _selectedPropertyTypes.value.map { it.id }.toString(),
//            repairType = _selectedRepairTypes.value.map { it.id }.toString(),
            status = _status.value,
//            roomNumber = _roomNumber.value.map { it }.toString() ,
//            floorNumber = _floorNumber.value.map { it }.toString(),
            sortBy = _sortBy.value,
            sortOrder = _sortOrder.value,
            limit = LIMIT_REGULAR.toInt(),
            offset = 0
        )
    }
}
