package com.mekanly.presentation.ui.business

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mekanly.BusinessType
import com.mekanly.R
import com.mekanly.data.DataItemBusinessProfile
import com.mekanly.data.DataSubBusinessItem
import com.mekanly.databinding.FragmentSubBusinessBinding
import com.mekanly.presentation.ui.adapters.AdapterBusinessCarpet
import com.mekanly.presentation.ui.adapters.AdapterBusinessChandelier
import com.mekanly.presentation.ui.adapters.AdapterBusinessConstruction
import com.mekanly.presentation.ui.adapters.AdapterItemBusinessProfile
import com.mekanly.presentation.ui.adapters.AdapterBusinessFurniture
import com.mekanly.presentation.ui.adapters.AdapterBusinessHousehold
import com.mekanly.presentation.ui.adapters.AdapterBusinessRealEstate


class SubBusinessFragment : Fragment() {
    private lateinit var binding: FragmentSubBusinessBinding
    private var currentBusinessType: BusinessType? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Получаем тип бизнеса из аргументов
        currentBusinessType = arguments?.getSerializable("business_type") as? BusinessType
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentSubBusinessBinding.inflate(inflater, container, false)


        initListeners()

        val items = listOf(
            DataItemBusinessProfile(
                R.drawable.rowach_mebel, "Rowaç mebel", "Gyssagly satlyk jaý gerek!!!"
            ),
            DataItemBusinessProfile(R.drawable.home_villa_image, "Täze öý", "Jaý amatly we täze."),
            DataItemBusinessProfile(
                R.drawable.rowach_mebel, "Rowaç mebel", "Gyssagly satlyk jaý gerek!!!"
            ),
            DataItemBusinessProfile(R.drawable.home_villa_image, "Täze öý", "Jaý amatly we täze."),
            DataItemBusinessProfile(
                R.drawable.rowach_mebel, "Rowaç mebel", "Gyssagly satlyk jaý gerek!!!"
            ),
            DataItemBusinessProfile(R.drawable.home_villa_image, "Täze öý", "Jaý amatly we täze."),
            DataItemBusinessProfile(
                R.drawable.model_house, "Satlyk dükan", "Dükan doly enjamlaşdyrylan."
            )
        )

        val adapter = AdapterItemBusinessProfile(items)
        binding.businessNotice.layoutManager = LinearLayoutManager(requireContext())
        binding.businessNotice.adapter = adapter







        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Устанавливаем адаптер в зависимости от типа бизнеса
        val adapter = when (currentBusinessType) {
            BusinessType.REAL_ESTATE -> createRealEstateAdapter()
            BusinessType.FURNITURE -> createBusinessFurnitureAdapter()
            BusinessType.HOUSEHOLD_APPLIANCES -> createHouseholdAdapter()
            BusinessType.CARPET_AND_RUGS -> createCarpetAdapter()
            BusinessType.CHANDELIERS -> createChandelierAdapter()
            BusinessType.CONSTRUCTION -> createConstructionAdapter()
            else -> null
        }

        binding.recyclerView.apply {
            layoutManager = GridLayoutManager(requireContext(), 3)
            this.adapter = adapter
        }
    }

    // Методы создания адаптеров с mock-данными
    private fun createRealEstateAdapter(): RecyclerView.Adapter<*> {
        val realEstateItems = listOf(

            DataSubBusinessItem(
                id = 1, name = "12312312312", imageResId = R.drawable.business_profile_image_house
            ),

            DataSubBusinessItem(
                id = 2, name = "12312312312", imageResId = R.drawable.business_profile_image_house
            ),

            DataSubBusinessItem(
                id = 3, name = "12312312312", imageResId = R.drawable.business_profile_image_house
            ),

            DataSubBusinessItem(
                id = 4, name = "12312312312", imageResId = R.drawable.business_profile_image_house
            ),

            DataSubBusinessItem(
                id = 3, name = "Aman", imageResId = R.drawable.business_profile_image_house
            ),

            DataSubBusinessItem(
                id = 3, name = "Aman", imageResId = R.drawable.business_profile_image_house
            ),

            DataSubBusinessItem(
                id = 3, name = "Aman", imageResId = R.drawable.business_profile_image_house
            ),

            DataSubBusinessItem(
                id = 3, name = "Aman", imageResId = R.drawable.business_profile_image_house
            ),

            DataSubBusinessItem(
                id = 3, name = "Aman", imageResId = R.drawable.business_profile_image_house
            ),

            // Создайте ваши SubFragmentItem для недвижимости
        )
        return AdapterBusinessRealEstate(realEstateItems) { item ->

        }
    }

    private fun createBusinessFurnitureAdapter(): RecyclerView.Adapter<*> {
        val businessActivityItems = listOf(

            DataSubBusinessItem(
                id = 1, name = "divan", imageResId = R.drawable.divan
            ),

            DataSubBusinessItem(
                id = 2, name = "divan", imageResId = R.drawable.divan
            ),

            DataSubBusinessItem(
                id = 3, name = "divan", imageResId = R.drawable.divan
            ),

            DataSubBusinessItem(
                id = 4, name = "divan", imageResId = R.drawable.divan
            ),

            DataSubBusinessItem(
                id = 3, name = "divan", imageResId = R.drawable.divan
            ),

            DataSubBusinessItem(
                id = 3, name = "divan", imageResId = R.drawable.divan
            ),

            DataSubBusinessItem(
                id = 3, name = "divan", imageResId = R.drawable.divan
            ),

            DataSubBusinessItem(
                id = 3, name = "divan", imageResId = R.drawable.divan
            ),

            DataSubBusinessItem(
                id = 3, name = "divan", imageResId = R.drawable.divan
            ),


            // Создайте ваши SubFragmentItem для бизнес-активности
        )
        return AdapterBusinessFurniture(businessActivityItems) { item ->
            // Обработка клика на элемент бизнес-активности
        }
    }

    private fun createHouseholdAdapter(): RecyclerView.Adapter<*> {
        val productionItems = listOf(


            DataSubBusinessItem(
                id = 1, name = "Aman", imageResId = R.drawable.washer_mashine
            ),

            DataSubBusinessItem(
                id = 2, name = "Aman", imageResId = R.drawable.washer_mashine
            ),

            DataSubBusinessItem(
                id = 3, name = "Aman", imageResId = R.drawable.washer_mashine
            ),

            DataSubBusinessItem(
                id = 4, name = "Aman", imageResId = R.drawable.washer_mashine
            ),

            DataSubBusinessItem(
                id = 3, name = "Aman", imageResId = R.drawable.washer_mashine
            ),

            DataSubBusinessItem(
                id = 3, name = "Aman", imageResId = R.drawable.washer_mashine
            ),

            DataSubBusinessItem(
                id = 3, name = "Aman", imageResId = R.drawable.washer_mashine
            ),

            DataSubBusinessItem(
                id = 3, name = "Aman", imageResId = R.drawable.washer_mashine
            ),

            DataSubBusinessItem(
                id = 3, name = "Aman", imageResId = R.drawable.washer_mashine
            ),



            // Создайте ваши SubFragmentItem для производства
        )
        return AdapterBusinessHousehold(productionItems) { item ->
            // Обработка клика на элемент производства
        }
    }

    private fun createCarpetAdapter(): RecyclerView.Adapter<*> {
        val productionItems = listOf(


            DataSubBusinessItem(
                id = 1, name = "Aman", imageResId = R.drawable.carpet
            ),

            DataSubBusinessItem(
                id = 2, name = "Aman", imageResId = R.drawable.carpet
            ),

            DataSubBusinessItem(
                id = 3, name = "Aman", imageResId = R.drawable.carpet
            ),

            DataSubBusinessItem(
                id = 4, name = "Aman", imageResId = R.drawable.carpet
            ),

            DataSubBusinessItem(
                id = 3, name = "Aman", imageResId = R.drawable.carpet
            ),

            DataSubBusinessItem(
                id = 3, name = "Aman", imageResId = R.drawable.carpet
            ),

            DataSubBusinessItem(
                id = 3, name = "Aman", imageResId = R.drawable.carpet
            ),

            DataSubBusinessItem(
                id = 3, name = "Aman", imageResId = R.drawable.carpet
            ),

            DataSubBusinessItem(
                id = 3, name = "Aman", imageResId = R.drawable.carpet
            ),



            // Создайте ваши SubFragmentItem для производства
        )
        return AdapterBusinessCarpet(productionItems) { item ->
            // Обработка клика на элемент производства
        }
    }

    private fun createChandelierAdapter(): RecyclerView.Adapter<*> {
        val productionItems = listOf(


            DataSubBusinessItem(
                id = 1, name = "Aman", imageResId = R.drawable.image_chandelier
            ),

            DataSubBusinessItem(
                id = 2, name = "Aman", imageResId = R.drawable.image_chandelier
            ),

            DataSubBusinessItem(
                id = 3, name = "Aman", imageResId = R.drawable.image_chandelier
            ),

            DataSubBusinessItem(
                id = 4, name = "Aman", imageResId = R.drawable.image_chandelier
            ),

            DataSubBusinessItem(
                id = 3, name = "Aman", imageResId = R.drawable.image_chandelier
            ),

            DataSubBusinessItem(
                id = 3, name = "Aman", imageResId = R.drawable.image_chandelier
            ),

            DataSubBusinessItem(
                id = 3, name = "Aman", imageResId = R.drawable.image_chandelier
            ),

            DataSubBusinessItem(
                id = 3, name = "Aman", imageResId = R.drawable.image_chandelier
            ),

            DataSubBusinessItem(
                id = 3, name = "Aman", imageResId = R.drawable.image_chandelier
            ),



            // Создайте ваши SubFragmentItem для производства
        )
        return AdapterBusinessChandelier(productionItems) { item ->
            // Обработка клика на элемент производства
        }
    }

    private fun createConstructionAdapter(): RecyclerView.Adapter<*> {
        val productionItems = listOf(


            DataSubBusinessItem(
                id = 1, name = "Aman", imageResId = R.drawable.image_drill
            ),

            DataSubBusinessItem(
                id = 2, name = "Aman", imageResId = R.drawable.image_drill
            ),

            DataSubBusinessItem(
                id = 3, name = "Aman", imageResId = R.drawable.image_drill
            ),

            DataSubBusinessItem(
                id = 4, name = "Aman", imageResId = R.drawable.image_drill
            ),

            DataSubBusinessItem(
                id = 3, name = "Aman", imageResId = R.drawable.image_drill
            ),

            DataSubBusinessItem(
                id = 3, name = "Aman", imageResId = R.drawable.image_drill
            ),

            DataSubBusinessItem(
                id = 3, name = "Aman", imageResId = R.drawable.image_drill
            ),

            DataSubBusinessItem(
                id = 3, name = "Aman", imageResId = R.drawable.image_drill
            ),

            DataSubBusinessItem(
                id = 3, name = "Aman", imageResId = R.drawable.image_drill
            ),



            // Создайте ваши SubFragmentItem для производства
        )
        return AdapterBusinessConstruction(productionItems) { item ->
            // Обработка клика на элемент производства
        }
    }



    private fun initListeners(){

        binding.backBtn.setOnClickListener{

            findNavController().popBackStack()

        }

    }



}