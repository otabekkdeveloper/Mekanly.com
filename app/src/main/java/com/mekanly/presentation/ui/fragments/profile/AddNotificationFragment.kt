package com.mekanly.presentation.ui.fragments.profile

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.google.android.material.tabs.TabLayoutMediator
import com.mekanly.R
import com.mekanly.databinding.FragmentAddNotificationBinding
import com.mekanly.presentation.ui.adapters.pagerAdapters.NotificationViewPager
import com.mekanly.presentation.ui.adapters.pagerAdapters.ViewPagerAdapter
import com.mekanly.presentation.ui.bottomSheet.SectionSelectionBottomSheet
import com.mekanly.presentation.ui.bottomSheet.ToCorrectBottomSheet


class AddNotificationFragment : Fragment() {
    private lateinit var binding: FragmentAddNotificationBinding
    private lateinit var viewPagerAdapter: NotificationViewPager


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {

        binding = FragmentAddNotificationBinding.inflate(inflater, container, false)


        binding.icShare.setOnClickListener {
            shareContent()
        }


        binding.toCorrect.setOnClickListener {
            val bottomSheet = ToCorrectBottomSheet()

            bottomSheet.show(childFragmentManager, "CustomBottomSheet")
        }


        viewPagerAdapter = NotificationViewPager(requireActivity())
        binding.viewPager.adapter = viewPagerAdapter
        binding.viewPager.isUserInputEnabled = false



        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            when (position) {
                0 -> {
                    tab.text = "Bildirişler"
                }

                1 -> {
                    tab.text = "Gözleýän jaýlarym"
                }

                2 -> {
                    tab.text = "Arhiwlenen"
                }
            }
        }.attach()


        binding.back.setOnClickListener {

            findNavController().popBackStack()

        }



        return binding.root
    }

    private fun shareContent() {
        val shareIntent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, "Текст, который вы хотите поделиться")
            type = "text/plain"
        }
        startActivity(Intent.createChooser(shareIntent, "Поделиться через"))
    }
}
