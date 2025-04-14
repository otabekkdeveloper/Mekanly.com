package com.mekanly.ui.fragments.profile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.mekanly.R
import com.mekanly.data.ProfileNotificationsData
import com.mekanly.databinding.FragmentAddNotificationBinding
import com.mekanly.presentation.ui.adapters.ProfileNotificationsAdapter
import com.mekanly.utils.itemdecorators.GridSpacingItemDecoration


class AddNotificationFragment : Fragment() {

    private var _binding: FragmentAddNotificationBinding? = null
    private val binding get() = _binding!!

    private lateinit var propertyAdapter: ProfileNotificationsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddNotificationBinding.inflate(inflater, container, false)

        binding.addHouse.setOnClickListener{
            findNavController().navigate(R.id.action_addNotificationFragment_to_fragmentAddHouse)
        }

        binding.backBtn.setOnClickListener {
            findNavController().popBackStack()
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        loadProperties()
    }

    private fun setupRecyclerView() {
        propertyAdapter = ProfileNotificationsAdapter(
            onItemClick = { property ->
                navigateToPropertyDetails(property.id)
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

    private fun loadProperties() {

        val sampleProperties = getSampleProperties()
        propertyAdapter.submitList(sampleProperties)
    }

    private fun navigateToPropertyDetails(propertyId: String) {

        // findNavController().navigate(PropertyListFragmentDirections.actionToPropertyDetails(propertyId))
    }

    private fun toggleFavorite(property: ProfileNotificationsData) {
        property.isFavorite = !property.isFavorite
        propertyAdapter.notifyItemChanged(propertyAdapter.currentList.indexOf(property))
    }

    private fun getSampleProperties(): List<ProfileNotificationsData> {
        return listOf(
            ProfileNotificationsData(
                id = "1",
                title = "Kwartira",
                address = "Aşgabat-parahat 1",
                time = "Şu gün 16:16",
                price = "133800",
                status = "Kabul edilmedi",
                images = listOf(
                    "https://example.com/image1.jpg",
                    "https://example.com/image2.jpg"
                )
            ),
            ProfileNotificationsData(
                id = "1",
                title = "Kwartira",
                address = "Aşgabat-parahat 1",
                time = "Şu gün 16:16",
                price = "133800",
                status = "Kabul edilmedi",
                images = listOf(
                    "https://example.com/image1.jpg",
                    "https://example.com/image2.jpg"
                )
            ),
            ProfileNotificationsData(
                id = "1",
                title = "Kwartira",
                address = "Aşgabat-parahat 1",
                time = "Şu gün 16:16",
                price = "133800",
                status = "Kabul edilmedi",
                images = listOf(
                    "https://example.com/image1.jpg",
                    "https://example.com/image2.jpg"
                )
            ),
            ProfileNotificationsData(
                id = "2",
                title = "Jaý",
                address = "Aşgabat-parahat 2",
                time = "Düýn 14:30",
                price = "250000",
                status = "Kabul edildi",
                images = listOf(
                    "https://example.com/image3.jpg",
                    "https://example.com/image4.jpg"
                )
            )
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
