package com.mekanly.presentation.ui.fragments.profile.addHouse.subFragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.mekanly.R
import com.mekanly.data.SearchingHousesData
import com.mekanly.databinding.FragmentSubSearchingHousesBinding
import com.mekanly.presentation.ui.adapters.SearchingHousesAdapter

class SubSearchingHousesFragment : Fragment() {
    private lateinit var binding: FragmentSubSearchingHousesBinding
    private val adList = mutableListOf<SearchingHousesData>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSubSearchingHousesBinding.inflate(inflater, container, false)


        val items = listOf(
            SearchingHousesData("Satlyк jaý gözleýän", "Aşgabat/ mkr1, mir1, mir2", "Otag 1", "Elitga", "Gyssagly satlyк jaý gereк!!!", "Kabul edildi", "10000-10000 TMT"),
            SearchingHousesData("Satlyк jaý gözleýän", "Aşgabat/ mkr1, mir1, mir2", "Otag 1", "Elitga", "Gyssagly satlyк jaý gereк!!!", "Kabul edildi", "10000-10000 TMT"),
            SearchingHousesData("Satlyк jaý gözleýän", "Aşgabat/ mkr1, mir1, mir2", "Otag 1", "Elitga", "Gyssagly satlyк jaý gereк!!!", "Kabul edildi", "10000-10000 TMT"),
            SearchingHousesData("Satlyк jaý gözleýän", "Aşgabat/ mkr1, mir1, mir2", "Otag 1", "Elitga", "Gyssagly satlyк jaý gereк!!!", "Kabul edildi", "10000-10000 TMT"),
            SearchingHousesData("Satlyк jaý gözleýän", "Aşgabat/ mkr1, mir1, mir2", "Otag 1", "Elitga", "Gyssagly satlyк jaý gereк!!!", "Kabul edildi", "10000-10000 TMT"),
            SearchingHousesData("Satlyк jaý gözleýän", "Aşgabat/ mkr1, mir1, mir2", "Otag 1", "Elitga", "Gyssagly satlyк jaý gereк!!!", "Kabul edildi", "10000-10000 TMT"),
            SearchingHousesData("Satlyк jaý gözleýän", "Aşgabat/ mkr1, mir1, mir2", "Otag 1", "Elitga", "Gyssagly satlyк jaý gereк!!!", "Kabul edildi", "10000-10000 TMT"),
            SearchingHousesData("Satlyк jaý gözleýän", "Aşgabat/ mkr1, mir1, mir2", "Otag 1", "Elitga", "Gyssagly satlyк jaý gereк!!!", "Kabul edildi", "10000-10000 TMT")
        )

        val adapter = SearchingHousesAdapter(requireContext(), items)
        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(context)




        binding.addSearchingHouses.setOnClickListener{

            findNavController().navigate(R.id.action_addNotificationFragment_to_fragmentAddSearchingHouses)

        }







        return binding.root
    }

}