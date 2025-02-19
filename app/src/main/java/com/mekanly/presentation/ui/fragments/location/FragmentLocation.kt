package com.mekanly.presentation.ui.fragments.location

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.mekanly.data.repository.RepositoryLocation
import com.mekanly.data.responseBody.ResponseLocation
import com.mekanly.databinding.FragmentLocationBinding
import com.mekanly.presentation.ui.adapters.AdapterLocations

class FragmentLocation : Fragment() {

    private var _binding: FragmentLocationBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: AdapterLocations
    private val dataList = mutableListOf<ResponseLocation>()
    private val repository = RepositoryLocation()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLocationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Настраиваем RecyclerView
//        adapter = AdapterLocations(requireContext(), dataList)
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.adapter = adapter

        // Загружаем данные
        loadDataFromApi()
    }

    private fun loadDataFromApi() {
        repository.getLocations(
            callback = { locations ->
                locations?.let {
                    dataList.clear()
//                    dataList.addAll(it)
                    adapter.notifyDataSetChanged()
                }
            },
            onFailure = { error ->
                println("Ошибка загрузки данных: ${error.message}")
            }
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
