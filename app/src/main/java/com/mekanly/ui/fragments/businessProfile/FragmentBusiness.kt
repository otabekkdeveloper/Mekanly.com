package com.mekanly.presentation.ui.fragments.businessProfile

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mekanly.R
import com.mekanly.data.models.BusinessCategory
import com.mekanly.databinding.FragmentBusinessBinding
import com.mekanly.presentation.ui.adapters.AdapterItemBusinessCategories
import com.mekanly.presentation.ui.enums.BusinessType
import com.mekanly.presentation.ui.fragments.businessProfile.adapter.AdapterBusinessProfilesPaginated
import com.mekanly.presentation.ui.fragments.businessProfile.viewModel.FragmentBusinessProfileState
import com.mekanly.presentation.ui.fragments.businessProfile.viewModel.VMBusinessProfiles
import com.mekanly.presentation.ui.fragments.flow.FragmentFlowDirections
import com.mekanly.utils.itemdecorators.GridSpacingItemDecoration
import com.mekanly.utils.itemdecorators.SpacingItemDecoration
import com.mekanly.utils.extensions.showErrorSnackBar
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class FragmentBusiness : Fragment() {
    private lateinit var binding: FragmentBusinessBinding
    private val viewModel by viewModels<VMBusinessProfiles>()
    private var adapter: AdapterBusinessProfilesPaginated? = null

    companion object {
        private const val TAG = "FragmentBusiness"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentBusinessBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d(TAG, "onViewCreated called")

        setupRecyclerView()
        initListeners()
        observeViewModel()
        loadInitialData()
    }

    private fun setupRecyclerView() {
        Log.d(TAG, "Setting up RecyclerView...")

        adapter = AdapterBusinessProfilesPaginated(viewModel, findNavController())

        binding.rvBusinessProfiles.apply {
            adapter = this@FragmentBusiness.adapter
            layoutManager = LinearLayoutManager(requireContext())

            if (itemDecorationCount == 0) {
                addItemDecoration(SpacingItemDecoration(5F))
            }
        }

        Log.d(TAG, "RecyclerView setup completed")
    }

    private fun loadInitialData() {
        Log.d(TAG, "loadInitialData() called")

        try {
            // Проверяем текущее состояние ViewModel
            val currentProfiles = viewModel.businessProfiles.value
            val currentState = viewModel.fragmentState.value

            Log.d(TAG, "Current profiles count: ${currentProfiles.size}")
            Log.d(TAG, "Current state: $currentState")

            if (currentProfiles.isEmpty()) {
                Log.d(TAG, "Loading initial data with start=0...")
                viewModel.getPageInfoDefault(0)
            } else {
                Log.d(TAG, "Data already exists, updating adapter")
                adapter?.updateList(currentProfiles)
            }
        } catch (e: Exception) {
            Log.e(TAG, "Error in loadInitialData: ${e.message}", e)
        }
    }

    private fun initListeners() {
        Log.d(TAG, "Initializing listeners...")

        binding.rvBusinessProfiles.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                if (dy <= 0) return

                val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                val visibleItemCount = layoutManager.childCount
                val totalItemCount = layoutManager.itemCount
                val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()
                val isNearBottom =
                    (visibleItemCount + firstVisibleItemPosition) >= totalItemCount - 2

                if (isNearBottom && viewModel.canLoadMore() && totalItemCount > 0) {
                    val currentSize = viewModel.businessProfiles.value.size
                    Log.d(TAG, "Loading more items. Current size: $currentSize")
                    viewModel.getPageInfoDefault(currentSize)
                }
            }
        })

        binding.inputSearch.setOnClickListener {
            findNavController().navigate(R.id.action_fragmentHome_to_subSearchBusinessFragment)
        }
    }

    private fun observeViewModel() {
        Log.d(TAG, "Setting up ViewModel observers...")

        // Наблюдаем за состоянием фрагмента
        lifecycleScope.launch {
            viewModel.fragmentState.collectLatest { state ->
                Log.d(TAG, "Fragment state changed: $state")
                handleFragmentState(state)
            }
        }

        // Наблюдаем за списком бизнес-профилей
        lifecycleScope.launch {
            viewModel.businessProfiles.collectLatest { profiles ->
                Log.d(TAG, "Business profiles updated: ${profiles.size} items")

                if (profiles.isNotEmpty()) {
                    Log.d(TAG, "Updating adapter with ${profiles.size} profiles")
                    adapter?.updateList(profiles)

                    // Логируем первые несколько элементов для отладки
                    profiles.take(3).forEachIndexed { index, profile ->
                        Log.d(TAG, "Profile $index: id=${profile.id}, brand=${profile.brand}")
                    }
                } else {
                    Log.d(TAG, "Profiles list is empty")
                }
            }
        }
    }

    private fun handleFragmentState(state: FragmentBusinessProfileState) {
        when (state) {
            is FragmentBusinessProfileState.Error -> {
                binding.progressBar.visibility = View.GONE

                // Детальное логирование ошибки
                Log.e(TAG, "=== ERROR STATE DETAILS ===")
                Log.e(TAG, "Error code: ${state.error}")
                Log.e(TAG, "Error type: ${state.error::class.java.simpleName}")

                // Попробуем получить больше информации об ошибке
                when (state.error) {
                    is Exception -> {
                        Log.e(TAG, "Exception message: ${state.error.message}")
                        Log.e(TAG, "Exception cause: ${state.error.cause}")
                        state.error.printStackTrace()
                    }

                    is String -> {
                        Log.e(TAG, "String error: $state.error")
                    }

                    is Int -> {
                        Log.e(TAG, "Integer error code: ${state.error}")
                        Log.e(TAG, "Error meaning: ${getErrorCodeMeaning(state.error)}")
                    }

                    else -> {
                        Log.e(TAG, "Unknown error type: ${state.error}")
                    }
                }
                Log.e(TAG, "=== END ERROR DETAILS ===")

                // Показываем пользователю понятное сообщение
                val errorMessage = getReadableErrorMessage(state.error)
                requireContext().showErrorSnackBar(binding.root, errorMessage)

                // Можно попробовать повторить запрос через некоторое время
                // retryAfterDelay()
            }

            FragmentBusinessProfileState.Loading -> {
                binding.progressBar.visibility = View.VISIBLE
                Log.d(TAG, "Loading state - showing progress bar")
            }

            is FragmentBusinessProfileState.SuccessCategories -> {
                binding.progressBar.visibility = View.GONE
                Log.d(TAG, "Categories loaded successfully: ${state.dataResponse.size}")
                setupCategoriesAdapter(state.dataResponse)
            }

            is FragmentBusinessProfileState.SuccessBusinessProfiles -> {
                binding.progressBar.visibility = View.GONE
                Log.d(TAG, "Business profiles loaded successfully: ${state.dataResponse.size}")

                // Дополнительная информация
                state.dataResponse.forEachIndexed { index, profile ->
                    Log.d(TAG, "Loaded profile $index: ${profile.brand} (id: ${profile.id})")
                }
            }

            else -> {
                Log.d(TAG, "Unknown state: $state")
            }
        }
    }

    private fun getErrorCodeMeaning(errorCode: Int): String {
        return when (errorCode) {
            1 -> "Network error"
            2 -> "Server error"
            3 -> "Authentication error / Invalid API key"
            4 -> "Not found"
            5 -> "Permission denied"
            6 -> "Rate limit exceeded"
            7 -> "Invalid parameters"
            8 -> "Timeout"
            else -> "Unknown error code"
        }
    }

    private fun getReadableErrorMessage(error: Any): String {
        return when (error) {
            is Int -> {
                when (error) {
                    3 -> "Ошибка авторизации. Проверьте подключение к интернету."
                    1 -> "Проблемы с сетью. Проверьте подключение."
                    2 -> "Проблемы с сервером. Попробуйте позже."
                    else -> "Про    изошла ошибка (код: $error)"
                }
            }

            is String -> error
            is Exception -> error.message ?: "Произошла непредвиденная ошибка"
            else -> "Произошла ошибка: $error"
        }
    }

    private fun retryAfterDelay() {
        lifecycleScope.launch {
            kotlinx.coroutines.delay(3000) // Ждем 3 секунды
            Log.d(TAG, "Retrying to load data...")
            viewModel.getPageInfoDefault(0)
        }
    }

    private fun setupCategoriesAdapter(list: List<BusinessCategory>) {
        Log.d(TAG, "Setting up categories adapter with ${list.size} items")

        val categoryAdapter = AdapterItemBusinessCategories(list) { selectedItem ->
            val action = FragmentFlowDirections.actionFragmentHomeToSubBusinessFragment(
                categoryId = selectedItem.id,
                title = selectedItem.title ?: "N/A",
                businessType = selectedItem.type?.name ?: BusinessType.FURNITURE.name
            )
            findNavController().navigate(action)
        }

        binding.categoriesRV.apply {
            layoutManager = GridLayoutManager(requireContext(), 3)
            if (itemDecorationCount == 0) {
                addItemDecoration(
                    GridSpacingItemDecoration(
                        spanCount = 3, spacingInDp = 6F, includeEdge = true
                    )
                )
            }
            adapter = categoryAdapter
        }

        Log.d(TAG, "Categories adapter setup completed")
    }

    override fun onDestroyView() {
        Log.d(TAG, "onDestroyView called")
        super.onDestroyView()
        adapter = null
    }
}

// Дополнительно - создайте тестовый метод в ViewModel для проверки API
// В VMBusinessProfiles добавьте:
/*
fun testApiConnection() {
    viewModelScope.launch {
        try {
            Log.d("VMBusinessProfiles", "Testing API connection...")

            // Ваш API вызов здесь
            val result = repository.getBusinessProfiles(start = 0, limit = 1)

            if (result.isSuccessful) {
                Log.d("VMBusinessProfiles", "API test successful")
            } else {
                Log.e("VMBusinessProfiles", "API test failed: ${result.code()} - ${result.message()}")
            }
        } catch (e: Exception) {
            Log.e("VMBusinessProfiles", "API test exception: ${e.message}", e)
        }
    }
}
*/