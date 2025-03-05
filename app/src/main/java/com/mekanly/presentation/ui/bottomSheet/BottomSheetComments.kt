package com.mekanly.presentation.ui.bottomSheet

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.mekanly.R
import com.mekanly.data.DataComments
import com.mekanly.presentation.ui.adapters.CommentsAdapter
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class BottomSheetComments : BottomSheetDialogFragment() {

    private lateinit var commentAdapter: CommentsAdapter
    private val commentsList = mutableListOf<DataComments>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.bottom_sheet_comments, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerView = view.findViewById<RecyclerView>(R.id.comments_recycler_view)
        val etComment = view.findViewById<EditText>(R.id.comment_edit_text)
        val btnSendComment = view.findViewById<Button>(R.id.send_button)

        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        // Тестовые комментарии (можно заменить API)
        commentsList.add(DataComments("BFI404HNR", "13.10.2024, 20:21", "Reiltor haky näçe?", 1, 1))
        commentsList.add(DataComments("User123", "14.10.2024, 15:10", "Bahasy nache?", 3, 0))

        commentAdapter = CommentsAdapter(commentsList)
        recyclerView.adapter = commentAdapter

        // Обработчик нажатия на кнопку "Отправить"
        btnSendComment.setOnClickListener {
            val newCommentText = etComment.text.toString().trim()
            if (newCommentText.isNotEmpty()) {
                val newComment = DataComments("Вы", getCurrentTime(), newCommentText, 0, 0)
                commentsList.add(0, newComment) // Добавляем в начало списка
                commentAdapter.notifyItemInserted(0)
                recyclerView.scrollToPosition(0) // Прокрутка к новому комментарию
                etComment.text.clear() // Очищаем поле ввода
            }
        }
    }

    private fun getCurrentTime(): String {
        val sdf = SimpleDateFormat("dd.MM.yyyy, HH:mm", Locale.getDefault())
        return sdf.format(Date())
    }
}
