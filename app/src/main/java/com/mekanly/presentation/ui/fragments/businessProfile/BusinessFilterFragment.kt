package com.mekanly.presentation.ui.fragments.businessProfile

import android.os.Bundle
import android.view.Gravity
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.transition.Visibility
import com.mekanly.R
import com.mekanly.databinding.FragmentBusinessCategoriesBinding
import com.mekanly.databinding.FragmentBusinessFilterBinding


class BusinessFilterFragment : Fragment() {

    private lateinit var binding: FragmentBusinessFilterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {

        binding = FragmentBusinessFilterBinding.inflate(inflater, container, false)

        initListeners()



        return binding.root
    }

    private fun initListeners() {

        binding.apply {


            popupMenu.setOnClickListener { view ->
                showPopupMenu(view)
            }

            btnClose.setOnClickListener {
                parentFragmentManager.popBackStack()
            }


            buttonBolum.setOnClickListener {

                findNavController().navigate(R.id.action_businessFilterFragment_to_businessCategoriesFragment)

            }


            location.setOnClickListener {

                findNavController().navigate(R.id.action_businessCategoriesFragment2_to_businessLocationFragment)

            }


        }


    }

    private fun showPopupMenu(view: View) {


        val popupMenu = PopupMenu(requireContext(), view, Gravity.END or Gravity.BOTTOM)

        // Inflate the menu resource
        popupMenu.menuInflater.inflate(R.menu.popup_menu, popupMenu.menu)

        // Set click listener for menu items
        popupMenu.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.option_default -> {
                    binding.popupMenuText.text = "Saýlanmadyk"
                    binding.downThree.visibility = View.VISIBLE
                    true
                }

                R.id.option_price_asc -> {
                    binding.popupMenuText.text = "Arzandan gymmada"
                    binding.downThree.visibility = View.GONE
                    true
                }

                R.id.option_price_desc -> {
                    binding.popupMenuText.text = "Gymmatdan arzana"
                    binding.downThree.visibility = View.GONE
                    true
                }

                else -> false
            }
        }

        // Show the popup menu
        popupMenu.show()
    }

}