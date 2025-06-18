package com.mekanly.ui.fragments.favorite

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.mekanly.R
import com.mekanly.databinding.FragmentFavouriteBinding
import com.mekanly.domain.model.ResponseBodyState
import com.mekanly.ui.adapters.AdapterFavouriteHouses
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class FavouriteFragment : Fragment() {

    private var _binding: FragmentFavouriteBinding? = null
    private val binding get() = _binding!!

    private lateinit var housesAdapter: AdapterFavouriteHouses
    private lateinit var productsAdapter: AdapterFavouriteBusinessProfile
    private val viewModel: FavoritesViewModel by viewModels()

    // Текущий выбранный тип
    private var currentType = FavoriteType.HOUSES

    enum class FavoriteType {
        HOUSES, BUSINESS
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavouriteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        setupRadioGroup()
        observeData()

        // Загружаем начальные данные
        viewModel.getFavorites()
    }

    private fun setupRecyclerView() {
        // Инициализируем адаптеры
        housesAdapter = AdapterFavouriteHouses(findNavController())
        productsAdapter = AdapterFavouriteBusinessProfile(findNavController())

        binding.recyclerViewProperties.apply {
            layoutManager = LinearLayoutManager(requireContext())
            // По умолчанию устанавливаем адаптер для домов
            adapter = housesAdapter
        }
    }

    private fun setupRadioGroup() {
        binding.radioGroup.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                R.id.radioBtnNotification -> {
                    currentType = FavoriteType.HOUSES
                    switchToHousesAdapter()
                }
                R.id.radioBtnBusinessProfile -> {
                    currentType = FavoriteType.BUSINESS
                    switchToBusinessAdapter()
                }
            }
        }
    }

    private fun switchToHousesAdapter() {
        binding.recyclerViewProperties.adapter = housesAdapter

        // Обновляем данные в адаптере
        val houses = viewModel.houses.value
        housesAdapter.setItems(houses)

        // Показываем/скрываем пустое состояние
        updateEmptyState(houses.isEmpty())
    }

    private fun switchToBusinessAdapter() {
        binding.recyclerViewProperties.adapter = productsAdapter

        // Обновляем данные в адаптере
        val products = viewModel.products.value
        productsAdapter.setItemsProduct(products)

        // Показываем/скрываем пустое состояние
        updateEmptyState(products.isEmpty())
    }

    private fun observeData() {
        lifecycleScope.launch {
            // Наблюдаем за состоянием UI
            viewModel.uiState.collectLatest { state ->
                when (state) {
                    is ResponseBodyState.Loading -> {
                        binding.progressBar.visibility = View.VISIBLE
                        binding.layNotFound.visibility = View.GONE
                    }

                    is ResponseBodyState.Error -> {
                        binding.progressBar.visibility = View.GONE
                        binding.layNotFound.visibility = View.VISIBLE
                    }

                    is ResponseBodyState.SuccessList -> {
                        binding.progressBar.visibility = View.GONE
                        updateCurrentAdapter()
                    }

                    else -> Unit
                }
            }
        }

        // Наблюдаем за изменениями в списке домов
        lifecycleScope.launch {
            viewModel.houses.collectLatest { houses ->
                if (currentType == FavoriteType.HOUSES) {
                    housesAdapter.setItems(houses)
                    updateEmptyState(houses.isEmpty())
                }
            }
        }

        // Наблюдаем за изменениями в списке товаров
        lifecycleScope.launch {
            viewModel.products.collectLatest { products ->
                if (currentType == FavoriteType.BUSINESS) {
                    productsAdapter.setItemsProduct(products)
                    updateEmptyState(products.isEmpty())
                }
            }
        }
    }

    private fun updateCurrentAdapter() {
        when (currentType) {
            FavoriteType.HOUSES -> {
                val houses = viewModel.houses.value
                housesAdapter.setItems(houses)
                updateEmptyState(houses.isEmpty())
            }
            FavoriteType.BUSINESS -> {
                val products = viewModel.products.value
                productsAdapter.setItemsProduct(products)
                updateEmptyState(products.isEmpty())
            }
        }
    }

    private fun updateEmptyState(isEmpty: Boolean) {
        binding.layNotFound.visibility = if (isEmpty) View.VISIBLE else View.GONE
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}