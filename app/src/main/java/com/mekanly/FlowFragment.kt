package com.mekanly

import com.mekanly.adapters.ImageAdapter
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.mekanly.adapters.PostAdapter
import com.mekanly.databinding.FragmentFlowBinding

class FlowFragment : Fragment() {
    private lateinit var binding: FragmentFlowBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFlowBinding.inflate(inflater, container, false)

        val images = listOf(
            R.drawable.image1,
            R.drawable.image1,
            R.drawable.image1,
            R.drawable.image1,
            R.drawable.image1,
            R.drawable.image1,
            R.drawable.image1,
            R.drawable.image1,
            R.drawable.image1,
            R.drawable.image1,
            R.drawable.image1,
            R.drawable.image1,
            R.drawable.image1,
            R.drawable.image1,
            R.drawable.image1,
            R.drawable.image1,
            R.drawable.image1,
            R.drawable.image1,
            R.drawable.image1,
            R.drawable.image1,
            R.drawable.image1,
            R.drawable.image1,
            R.drawable.image1,
            R.drawable.image1,
            R.drawable.image1,
            R.drawable.image1,
            R.drawable.image1,
            R.drawable.image1,
            R.drawable.image1,
            R.drawable.image1,
            R.drawable.image1)

        // Устанавливаем адаптер
        binding.recyclerView.adapter = ImageAdapter(images)

        // Устанавливаем горизонтальный LayoutManager
        binding.recyclerView.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)


        val imagesTwo = listOf(
            R.drawable.rv_vertical_home_page,
            R.drawable.rv_vertical_home_page,
            R.drawable.rv_vertical_home_page,
            R.drawable.rv_vertical_home_page,
            R.drawable.rv_vertical_home_page,
            R.drawable.rv_vertical_home_page,
            R.drawable.rv_vertical_home_page,
            R.drawable.rv_vertical_home_page,
            R.drawable.rv_vertical_home_page,
            R.drawable.rv_vertical_home_page,
            R.drawable.rv_vertical_home_page,
            R.drawable.rv_vertical_home_page,
            R.drawable.rv_vertical_home_page)

        // Устанавливаем адаптер
        binding.recyclerViewTwo.adapter = PostAdapter(imagesTwo)

        // Устанавливаем горизонтальный LayoutManager
        binding.recyclerViewTwo.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)



        return binding.root
    }
}
