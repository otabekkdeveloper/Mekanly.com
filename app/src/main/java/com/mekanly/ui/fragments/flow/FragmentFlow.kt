package com.mekanly.presentation.ui.fragments.flow

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayoutMediator
import com.mekanly.R
import com.mekanly.databinding.FragmentFlowBinding
import com.mekanly.presentation.ui.adapters.pagerAdapters.AdapterViewPager
import com.mekanly.ui.fragments.flow.VMFlow

class FragmentFlow : Fragment() {
    private lateinit var binding: FragmentFlowBinding
    private lateinit var viewPagerAdapter: AdapterViewPager
    private val vmFlow: VMFlow by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentFlowBinding.inflate(inflater, container, false)
        getGlobalOptions()


        viewPagerAdapter = AdapterViewPager(requireActivity())
        binding.viewPager.adapter = viewPagerAdapter
        binding.viewPager.isUserInputEnabled = false

        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            when (position) {
                0 -> {
                    tab.text = getString(R.string.home_page)
                    tab.setIcon(R.drawable.ic_home_page_unselected)
                }
                1 -> {
                    tab.text = getString(R.string.properties)
                    tab.setIcon(R.drawable.house_search)
                }
                2 -> {
                    tab.text = getString(R.string.business_profile)
                    tab.setIcon(R.drawable.ic_business_profile_unselected)
                }
                3 -> {
                    tab.text = getString(R.string.favorites)
                    tab.setIcon(R.drawable.ic_favourite)
                }
                4 -> {
                    tab.text = getString(R.string.menu)
                    tab.setIcon(R.drawable.menu_ic)
                }
            }
        }.attach()


        binding.tabLayout.getTabAt(0)?.setIcon(R.drawable.ic_home_page_selected)

        binding.tabLayout.addOnTabSelectedListener(object : com.google.android.material.tabs.TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: com.google.android.material.tabs.TabLayout.Tab) {
                // Изменение на активную иконку при выборе
                when (tab.position) {
                    0 -> tab.setIcon(R.drawable.ic_home_page_selected)
                    1 -> tab.setIcon(R.drawable.house_search_bold)
                    2 -> tab.setIcon(R.drawable.ic_shopping_bold)
                    3 -> tab.setIcon(R.drawable.heart_bold)
                    4 -> tab.setIcon(R.drawable.menu_ic)
                }

//                // Переход на выбранную вкладку без анимации
//                binding.viewPager.setCurrentItem(tab.position, false)
            }

            override fun onTabUnselected(tab: com.google.android.material.tabs.TabLayout.Tab) {
                // Возврат к обычной иконке
                when (tab.position) {
                    0 -> tab.setIcon(R.drawable.ic_home_page_unselected)
                    1 -> tab.setIcon(R.drawable.house_search)
                    2 -> tab.setIcon(R.drawable.ic_business_profile_unselected)
                    3 -> tab.setIcon(R.drawable.ic_favourite)
                    4 -> tab.setIcon(R.drawable.menu_ic)
                }
            }

            override fun onTabReselected(tab: com.google.android.material.tabs.TabLayout.Tab) {
                // Поведение при повторном нажатии на вкладку (если нужно)
            }
        })

//        binding.logoPlus.setOnClickListener { view ->
//            showMenu(view)
//        }

        return binding.root
    }

    private fun getGlobalOptions() {
        Log.e("GLOBAL_OPTIONS", "getGlobalOptions: ")
        vmFlow.getGlobalOptions()
    }

    // Метод для отключения свайпа
    private fun disableSwipe(viewPager2: ViewPager2) {
        viewPager2.isUserInputEnabled = false
    }

    // Метод для переключения на определенную страницу ViewPager
    fun navigateToPage(position: Int) {
        if (::binding.isInitialized && position >= 0 && position < viewPagerAdapter.itemCount) {
            binding.viewPager.currentItem = position
        }

    }





    }




