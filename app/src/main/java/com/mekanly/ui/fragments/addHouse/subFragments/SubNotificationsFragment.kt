package com.mekanly.presentation.ui.fragments.addHouse.subFragments


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.mekanly.R
import com.mekanly.data.ProfileNotificationsData
import com.mekanly.data.models.House
import com.mekanly.databinding.FragmentSubNotificationsBinding
import com.mekanly.ui.adapters.ProfileNotificationsAdapter

class SubNotificationsFragment : Fragment() {

    private var _binding: FragmentSubNotificationsBinding? = null
    private val binding get() = _binding!!

    private lateinit var propertyAdapter: ProfileNotificationsAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSubNotificationsBinding.inflate(inflater, container, false)


        binding.addHouse.setOnClickListener{

            findNavController().navigate(R.id.action_addNotificationFragment_to_fragmentAddHouse)
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
                // Навигация к деталям объекта
                navigateToPropertyDetails(property.id.toString())
            },
            onLikeClick = { property ->
                // Обработка нажатия на кнопку "Нравится"
                toggleFavorite(property)
            }
        )

        binding.recyclerViewProperties.apply {
            layoutManager = GridLayoutManager(requireContext(), 2)
            adapter = propertyAdapter
        }
    }

    private fun loadProperties() {
        // Здесь должна быть логика загрузки данных
        // Например, из ViewModel или Repository
//        val sampleProperties = getSampleProperties()
//        propertyAdapter.submitList(sampleProperties)
    }

    private fun navigateToPropertyDetails(propertyId: String) {
        // Навигация к экрану с деталями
        // Например, с использованием Navigation Component
        // findNavController().navigate(PropertyListFragmentDirections.actionToPropertyDetails(propertyId))
    }

    private fun toggleFavorite(property: House) {

//        property.liked = !property.liked
        propertyAdapter.notifyItemChanged(propertyAdapter.currentList.indexOf(property))
    }

    private fun getSampleProperties(): List<ProfileNotificationsData> {
        // Примеры данных для отладки
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






