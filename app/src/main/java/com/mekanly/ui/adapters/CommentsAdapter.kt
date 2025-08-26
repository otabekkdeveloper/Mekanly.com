package com.mekanly.presentation.ui.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.view.ContextThemeWrapper
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.PopupMenu
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.mekanly.R
import com.mekanly.data.models.Comment
import com.mekanly.databinding.ItemCommentsBinding

class CommentsAdapter(
    private val context: Context,
    val comments: MutableList<Comment>,
    private val onDeleteComment: (commentId: Long, position: Int) -> Unit,
    private val onUpdateComment: (commentId: Long, position: Int, newText: String) -> Unit
) : RecyclerView.Adapter<CommentsAdapter.CommentViewHolder>() {

    inner class CommentViewHolder(private val binding: ItemCommentsBinding) :
        RecyclerView.ViewHolder(binding.root) {

        @SuppressLint("SetTextI18n")
        fun bind(comment: Comment) {
            binding.userName.text = comment.user.username
            binding.date.text = comment.createdAt
            binding.commentText.text = comment.comment
            binding.likeCount.text = comment.like.toString()
            binding.dislikeCount.text = comment.dislike.toString()

            // Показываем меню только для комментариев владельца
            if (comment.isOwner) {
                binding.popupMenu.visibility = View.VISIBLE
                binding.popupMenu.setOnClickListener { view ->
                    showCommentPopupMenu(view, comment.id, adapterPosition)
                }
            } else {
                binding.popupMenu.visibility = View.GONE
            }

            if (comment.status == "pending") {
                binding.commentStatus.visibility = View.VISIBLE
            } else {
                binding.commentStatus.visibility = View.GONE
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentViewHolder {
        val binding = ItemCommentsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CommentViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CommentViewHolder, position: Int) {
        holder.bind(comments[position])
    }

    override fun getItemCount(): Int = comments.size

    private fun showCommentPopupMenu(view: View, commentId: Long, position: Int) {
        val wrapper = ContextThemeWrapper(context, R.style.CustomPopupMenu)
        val commentPopup = PopupMenu(wrapper, view, Gravity.END or Gravity.BOTTOM)

        commentPopup.menuInflater.inflate(R.menu.comment_popup, commentPopup.menu)

        commentPopup.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.deleteText -> {
                    showDeleteConfirmationDialog(commentId, position)
                    true
                }

                R.id.changeText -> {
                    showEditCommentDialog(commentId, position, comments[position].comment)
                    true
                }

                else -> false
            }
        }

        commentPopup.show()
    }

    private fun showDeleteConfirmationDialog(commentId: Long, position: Int) {
        MaterialAlertDialogBuilder(context)
            .setTitle("Удалить комментарий")
            .setMessage("Вы уверены, что хотите удалить этот комментарий?")
            .setPositiveButton("Удалить") { _, _ ->
                onDeleteComment(commentId, position)
            }
            .setNegativeButton("Отмена", null)
            .show()
    }

    private fun showEditCommentDialog(commentId: Long, position: Int, currentText: String) {
        val editText = EditText(context).apply {
            setText(currentText)
            setSelection(text.length) // Устанавливаем курсор в конец
        }

        MaterialAlertDialogBuilder(context)
            .setTitle("Редактировать комментарий")
            .setView(editText)
            .setPositiveButton("Сохранить") { _, _ ->
                val newText = editText.text.toString().trim()
                if (newText.isNotEmpty() && newText != currentText) {
                    onUpdateComment(commentId, position, newText)
                }
            }
            .setNegativeButton("Отмена", null)
            .show()
    }

    fun addItem(comment: Comment) {
        comments.add(comment)
        notifyItemInserted(comments.size - 1)
    }

    fun removeItem(position: Int) {
        if (position in 0 until comments.size) {
            comments.removeAt(position)
            notifyItemRemoved(position)
            // Обновляем позиции остальных элементов
            if (position < comments.size) {
                notifyItemRangeChanged(position, comments.size - position)
            }
        }
    }

    fun updateItem(position: Int, newComment: Comment) {
        if (position in 0 until comments.size) {
            comments[position] = newComment
            notifyItemChanged(position)
        }
    }

    fun getItem(position: Int): Comment? {
        return if (position in 0 until comments.size) {
            comments[position]
        } else {
            null
        }
    }

    // ДОБАВЛЕНО: Метод для очистки списка
    fun clear() {
        val size = comments.size
        comments.clear()
        notifyItemRangeRemoved(0, size)
    }

    // ДОБАВЛЕНО: Метод для добавления списка элементов
    fun addAll(newComments: List<Comment>) {
        val startPosition = comments.size
        comments.addAll(newComments)
        notifyItemRangeInserted(startPosition, newComments.size)
    }
}