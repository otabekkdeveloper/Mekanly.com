package com.mekanly.presentation.ui.bottomSheet

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.mekanly.R

class ToCorrectBottomSheet : BottomSheetDialogFragment() {

    private lateinit var userPhoto: ImageView
    private lateinit var addPhoto: LinearLayout
    private val sharedPrefKey = "user_photo_uri"

    private val pickImageLauncher =
        registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
            uri?.let {
                saveImageUri(it) // Сохраняем URI
                loadImage(it)    // Загружаем фото
            }
        }

    private var onCitySelected: ((String) -> Unit)? = null

    fun setOnCitySelectedListener(listener: (String) -> Unit) {
        onCitySelected = listener
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.bottom_sheet_to_correct, container, false)


        userPhoto = view.findViewById(R.id.user_photo)
        addPhoto = view.findViewById(R.id.addPhoto)

        addPhoto.setOnClickListener {
            pickImageLauncher.launch("image/*") // Открываем галерею
        }

        loadSavedImage()


        val closeIcon = view.findViewById<ImageView>(R.id.close)

        closeIcon.setOnClickListener {
            dismiss()
        }




        return view
    }

    // Сохранение URI в SharedPreferences
    private fun saveImageUri(uri: Uri) {
        val sharedPreferences =
            requireActivity().getSharedPreferences("profile_prefs", Context.MODE_PRIVATE)
        sharedPreferences.edit().putString(sharedPrefKey, uri.toString()).apply()
    }

    // Загрузка сохраненного изображения
    private fun loadSavedImage() {
        val sharedPreferences =
            requireActivity().getSharedPreferences("profile_prefs", Context.MODE_PRIVATE)
        val savedUri = sharedPreferences.getString(sharedPrefKey, null)
        savedUri?.let { loadImage(Uri.parse(it)) }
    }

    // Установка изображения и округление
    private fun loadImage(uri: Uri) {
        Glide.with(this).load(uri)
            .apply(RequestOptions.circleCropTransform()) // Делаем фото круглым
            .into(userPhoto)
    }


}
