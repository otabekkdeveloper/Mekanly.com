package com.mekanly.ui.fragments.addHouse

import android.content.Context
import android.net.Uri
import android.provider.OpenableColumns
import androidx.lifecycle.ViewModel
import com.mekanly.data.request.AddHouseBody
import com.mekanly.domain.model.ResponseBodyState
import com.mekanly.domain.useCase.AddHouseUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import java.io.File

class VMAddHouse : ViewModel() {

    private val _images = MutableStateFlow<List<Uri>>(emptyList())
    val images: StateFlow<List<Uri>> = _images


    private val useCase by lazy {
        AddHouseUseCase()
    }

    fun addHouse(addHouseBody: AddHouseBody, images:List<File>, callback: (ResponseBodyState) -> Unit) {
        useCase.addHouse(addHouseBody, images, callback)
    }

    fun addImages(newUris: List<Uri>) {
        val updated = (_images.value + newUris).take(15) // Ограничение 15
        _images.value = updated
    }

    fun removeImage(uri: Uri) {
        _images.value = _images.value.filterNot { it == uri }
    }

    fun clearImages() {
        _images.value = emptyList()
    }

    fun convertUrisToFiles(context: Context, uris: List<Uri>): List<File> {
        val fileList = mutableListOf<File>()

        for (uri in uris) {
            val fileName = getFileName(context, uri)
            val safeFileName = fileName.replace(Regex("[^a-zA-Z0-9._-]"), "_")
            val inputStream = context.contentResolver.openInputStream(uri) ?: continue

            val tempFile = File.createTempFile("upload_", safeFileName, context.cacheDir)

            inputStream.use { input ->
                tempFile.outputStream().use { output ->
                    input.copyTo(output)
                }
            }

            fileList.add(tempFile)
        }

        return fileList
    }


    fun getFileName(context: Context, uri: Uri): String {
        var name = "temp_image.jpg"
        val cursor = context.contentResolver.query(uri, null, null, null, null)
        cursor?.use {
            val nameIndex = it.getColumnIndex(OpenableColumns.DISPLAY_NAME)
            if (it.moveToFirst() && nameIndex >= 0) {
                name = it.getString(nameIndex)
            }
        }
        return name
    }

}