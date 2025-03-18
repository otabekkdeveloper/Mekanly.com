package com.mekanly.presentation.ui.fragments.profile.homeSeekers

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.mekanly.R
import com.mekanly.data.SearchingHousesData
import com.mekanly.databinding.FragmentHomeSeekersBinding
import com.mekanly.presentation.ui.adapters.SearchingHousesAdapter


class FragmentHomeSeekers : Fragment() {
    private lateinit var binding: FragmentHomeSeekersBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {

        binding = FragmentHomeSeekersBinding.inflate(inflater, container, false)


        binding.backBtn.setOnClickListener{
            findNavController().popBackStack()

        }

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

        val adapter = SearchingHousesAdapter(requireContext(), items, isTextView1Visible = false, isTextView2Visible = false)
        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(context)




        return binding.root

    }
}