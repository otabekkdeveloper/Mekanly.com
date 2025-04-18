package com.mekanly.ui.fragments.properties

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.mekanly.R
import com.mekanly.data.models.House
import com.mekanly.databinding.FragmentUserPropertiesBinding
import com.mekanly.domain.model.ResponseBodyState
import com.mekanly.ui.adapters.ProfileNotificationsAdapter
import com.mekanly.utils.itemdecorators.GridSpacingItemDecoration
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


class UserPropertiesFragment : Fragment() {

    private var _binding: FragmentUserPropertiesBinding? = null
    private val binding get() = _binding!!

    private lateinit var propertyAdapter: ProfileNotificationsAdapter

    private val viewModel: PropertiesViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentUserPropertiesBinding.inflate(inflater, container, false)

        binding.addHouse.setOnClickListener{
            findNavController().navigate(R.id.action_addNotificationFragment_to_fragmentAddHouse)
        }

        binding.backBtn.setOnClickListener {
            findNavController().popBackStack()
        }

        lifecycleScope.launch {
            viewModel.uiState.collectLatest {
                when (it) {
                    is ResponseBodyState.Error -> {
                        binding.progressBar.visibility = View.GONE
                    }

                    ResponseBodyState.Loading -> {
                        binding.progressBar.visibility = View.VISIBLE
                    }

                    is ResponseBodyState.SuccessList -> {
                        binding.progressBar.visibility = View.GONE
                        setAdapter(viewModel.houses.value)
                    }

                    else -> {}
                }
            }
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
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
