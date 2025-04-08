package com.mekanly.presentation.ui.bottomSheet

import LocationBottomSheet
import android.app.AlertDialog
import android.app.FragmentManager
import android.content.Context
import android.view.LayoutInflater
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import com.mekanly.R
import com.mekanly.data.PropertiesDialogData
import com.mekanly.databinding.BottomSheetLocationBinding
import com.mekanly.databinding.FragmentBusinessFilterBottomSheetBinding
import com.mekanly.databinding.FragmentDialogPropertiesBinding

class BusinessFilterFragmentBottomSheet(
    private val context: Context,
    private val fragmentManager: androidx.fragment.app.FragmentManager,
    private val onDelete : () ->Unit)
{
    private val binding: FragmentBusinessFilterBottomSheetBinding =
        FragmentBusinessFilterBottomSheetBinding.inflate(LayoutInflater.from(context))

    private val dialog: AlertDialog = AlertDialog.Builder(context)
        .setView(binding.root)
        .setCancelable(true)
        .create()


    init {

        binding.deleteText.apply{
            setOnClickListener{

                binding.apply {

                    locationText.text = "Saýlanmadyk"
                    bolumTextView.text = "Saýlanmadyk"
                    etMaxPrice.setText("")
                    etMinPrice.setText("")

                }

            }
        }




        binding.buttonBolum.setOnClickListener{

            val bottomSheet = SectionSelectionBottomSheet(onDelete = {
                binding.bolumTextView.text = "Saýlanmadyk"

            })

            bottomSheet.setOnCitySelectedListener { selectedCity ->

                binding.bolumTextView.text = selectedCity

            }
            bottomSheet.show(fragmentManager, "SectionSelectionManager")

        }

        binding.location.setOnClickListener{

            val cities = listOf(
                "Aşgabat şäheri",
                "Arkadag şäheri",
                "Mary welaýaty",
                "Daşoguz welaýaty",
                "Lebap welaýaty",
                "Balkan welaýaty",
                "Ahal welaýaty"
            )

            val locationBottomSheet = LocationBottomSheet(cities, onDelete = {

                binding.locationText.text = "Saýlanmadyk"


            }){selectedLocation ->

                binding.locationText.text = selectedLocation

            }

            locationBottomSheet.show(fragmentManager, "LocationBottomSheet")
        }


        binding.btnClose.setOnClickListener{
            onDelete()
            dialog.dismiss()
        }

    }

    fun show() {
        dialog.show()
    }
}
