package com.mekanly.presentation.ui.adapters

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mekanly.databinding.ItemImageDownloadBinding

class AdapterImageDownload(private val images: MutableList<Bitmap>, private var mainImageIndex: Int) :
    RecyclerView.Adapter<AdapterImageDownload.ViewHolder>() {

    class ViewHolder(val binding: ItemImageDownloadBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemImageDownloadBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return ViewHolder(binding)
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val bitmap = images[position]

        // Устанавливаем картинку
        holder.binding.imageView.setImageBitmap(bitmap)

        // Если это первое добавленное изображение, показываем текст "Главная картинка"
        if (position == 0) {
            holder.binding.mainImageLabel.visibility = View.VISIBLE
        } else {
            holder.binding.mainImageLabel.visibility = View.GONE
        }

        // Симуляция загрузки
        simulateLoading(holder, position)

        // Обработчик для удаления картинки
        holder.binding.deleteButton.setOnClickListener {
            val currentPosition = holder.adapterPosition
            if (currentPosition != RecyclerView.NO_POSITION) {
                images.removeAt(currentPosition)
                notifyItemRemoved(currentPosition)
                notifyItemRangeChanged(currentPosition, images.size)

                // Обновление главного изображения после удаления
                if (currentPosition == mainImageIndex) {
                    mainImageIndex = if (images.isNotEmpty()) 0 else -1
                    notifyDataSetChanged() // Перерисовываем адаптер
                }
            }
        }
    }

    override fun getItemCount(): Int = images.size

    // Метод для симуляции загрузки
    private fun simulateLoading(holder: ViewHolder, position: Int) {
        val handler = Handler(Looper.getMainLooper())
        var progress = 0

        val runnable = object : Runnable {
            @SuppressLint("SetTextI18n")
            override fun run() {
                if (progress < 100) {
                    progress += 10
                    holder.binding.progressBar.progress = progress
                    holder.binding.progressText.text = "$progress%"
                    handler.postDelayed(this, 200) // Задержка для симуляции
                } else {
                    // Загрузка завершена
                    holder.binding.progressBar.visibility = ViewGroup.GONE
                    holder.binding.progressText.visibility = ViewGroup.GONE
                    holder.binding.checkIcon.visibility = ViewGroup.VISIBLE
                    holder.binding.deleteButton.visibility = ViewGroup.VISIBLE
                }
            }
        }
        handler.post(runnable)
    }


}
