package com.mekanly.ui.fragments.favorite

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.mekanly.data.models.House
import com.mekanly.databinding.FragmentFavouriteBinding
import com.mekanly.databinding.FragmentUserPropertiesBinding
import com.mekanly.domain.model.ResponseBodyState
import com.mekanly.ui.adapters.ProfileNotificationsAdapter
import com.mekanly.ui.fragments.properties.PropertiesViewModel
import com.mekanly.utils.itemdecorators.GridSpacingItemDecoration
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


class FavouriteFragment : Fragment() {

    private var _binding: FragmentFavouriteBinding? = null
    private val binding get() = _binding!!

    private lateinit var propertyAdapter: ProfileNotificationsAdapter

    private val viewModel: FavoritesViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentFavouriteBinding.inflate(inflater, container, false)

        lifecycleScope.launch {
            viewModel.uiState.collectLatest {
                when (it) {
                    is ResponseBodyState.Error -> {
                        binding.progressBar.visibility = View.GONE
                        binding.layNotFound.visibility = View.VISIBLE
                    }

                    ResponseBodyState.Loading -> {
                        binding.progressBar.visibility = View.VISIBLE
                        binding.layNotFound.visibility = View.GONE
                    }

                    is ResponseBodyState.SuccessList -> {
                        binding.progressBar.visibility = View.GONE
                        if (viewModel.houses.value.isEmpty()){
                            binding.layNotFound.visibility = View.VISIBLE
                        }else{
                            setAdapter(viewModel.houses.value)
                        }
                    }

                    else -> {}
                }
            }
        }

        return binding.root
    }


    private fun setupRecyclerView() {
        propertyAdapter = ProfileNotificationsAdapter(
            onItemClick = { property ->
                navigateToPropertyDetails(property.id.toString())
            },
            onLikeClick = { property ->
                toggleFavorite(property)
            }
        )

        binding.recyclerViewProperties.apply {
            layoutManager = GridLayoutManager(requireContext(), 2)
            val itemDecoration = GridSpacingItemDecoration(
                spanCount = 2,
                spacingInDp = 10F,
                includeEdge = true
            )
            addItemDecoration(itemDecoration)
            adapter = propertyAdapter
        }
    }

    private fun setAdapter(value: List<House>) {
        propertyAdapter.submitList(value)
    }

    private fun navigateToPropertyDetails(propertyId: String) {
        // findNavController().navigate(PropertyListFragmentDirections.actionToPropertyDetails(propertyId))
    }

    private fun toggleFavorite(property: House) {
        property.liked = !property.liked
        propertyAdapter.notifyItemChanged(propertyAdapter.currentList.indexOf(property))
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}