package com.mekanly.presentation.ui.fragments.flow

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayoutMediator
import com.mekanly.R
import com.mekanly.databinding.FragmentFlowBinding
import com.mekanly.presentation.ui.adapters.pagerAdapters.ViewPagerAdapter

class FragmentFlow : Fragment() {
    private lateinit var binding: FragmentFlowBinding
    private lateinit var viewPagerAdapter: ViewPagerAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentFlowBinding.inflate(inflater, container, false)

        viewPagerAdapter = ViewPagerAdapter(requireActivity())
        binding.viewPager.adapter = viewPagerAdapter
        binding.viewPager.isUserInputEnabled = false


        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            when (position) {
                0 -> {
                    tab.text = getString(R.string.home_page)
                    tab.setIcon(R.drawable.house_logo)
                }
                1 -> {
                    tab.text = getString(R.string.properties)
                    tab.setIcon(R.drawable.search)
                }
                2 -> {
                    tab.text = getString(R.string.business_profile)
                    tab.setIcon(R.drawable.ic_shop)
                }
                3 -> {
                    tab.text = getString(R.string.favorites)
                    tab.setIcon(R.drawable.heart_icon)
                }
                4 -> {
                    tab.text = getString(R.string.menu)
                    tab.setIcon(R.drawable.menu_ic)
                }
            }
        }.attach()


        binding.tabLayout.getTabAt(0)?.setIcon(R.drawable.house)

        binding.tabLayout.addOnTabSelectedListener(object : com.google.android.material.tabs.TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: com.google.android.material.tabs.TabLayout.Tab) {
                // Изменение на активную иконку при выборе
                when (tab.position) {
                    0 -> tab.setIcon(R.drawable.house)
                    1 -> tab.setIcon(R.drawable.emlak_bold)
                    2 -> tab.setIcon(R.drawable.ic_shopping_bold)
                    3 -> tab.setIcon(R.drawable.heart_bold)
                    4 -> tab.setIcon(R.drawable.menu_ic)
                }

                // Переход на выбранную вкладку без анимации
                binding.viewPager.setCurrentItem(tab.position, false)
            }

            override fun onTabUnselected(tab: com.google.android.material.tabs.TabLayout.Tab) {
                // Возврат к обычной иконке
                when (tab.position) {
                    0 -> tab.setIcon(R.drawable.house_logo)
                    1 -> tab.setIcon(R.drawable.search)
                    2 -> tab.setIcon(R.drawable.ic_shop)
                    3 -> tab.setIcon(R.drawable.heart_icon)
                    4 -> tab.setIcon(R.drawable.menu_ic)
                }
            }

            override fun onTabReselected(tab: com.google.android.material.tabs.TabLayout.Tab) {
                // Поведение при повторном нажатии на вкладку (если нужно)
            }
        })


        // Настройка действия нажатия на логотип
        binding.logoPlus.setOnClickListener { view ->
            showMenu(view)
        }







        return binding.root
    }

    // Метод для отключения свайпа
    private fun disableSwipe(viewPager2: ViewPager2) {
        viewPager2.isUserInputEnabled = false
    }
    // Отображение меню
    @SuppressLint("DiscouragedPrivateApi")
    private fun showMenu(view: View) {
        val popupMenu = PopupMenu(requireContext(), view, 0)
        popupMenu.menuInflater.inflate(R.menu.home_menu, popupMenu.menu)
        try {
            val fieldPopup = PopupMenu::class.java.getDeclaredField("mPopup")
            fieldPopup.isAccessible = true
            val popup = fieldPopup.get(popupMenu)
            popup.javaClass.getDeclaredMethod("setForceShowIcon", Boolean::class.java)
                .invoke(popup, true)
        } catch (e: Exception) {
            e.printStackTrace()
        }

        // Обработка кликов на элементы меню
        popupMenu.setOnMenuItemClickListener { menuItem ->
            handleMenuItemClick(menuItem)
            true
        }

        popupMenu.show()
    }

    // Обработка кликов на элементы меню
    private fun handleMenuItemClick(menuItem: MenuItem) {
        when (menuItem.itemId) {
            R.id.menu_item_1 -> {
                // Переход на первый фрагмент
                findNavController().navigate(R.id.action_homeFragment_to_bildirishlerimFragment)
            }
            R.id.menu_item_2 -> {
                // Переход на второй фрагмент
                findNavController().navigate(R.id.action_homeFragment_to_bildirishlerimFragment)
            }
        }
    }

}
