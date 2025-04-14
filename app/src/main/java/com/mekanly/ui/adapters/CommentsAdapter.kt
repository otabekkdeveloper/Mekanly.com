package com.mekanly.presentation.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mekanly.data.models.Comment
import com.mekanly.databinding.ItemCommentsBinding

class CommentsAdapter(private val comments: List<Comment>) : RecyclerView.Adapter<CommentsAdapter.CommentViewHolder>() {

    inner class CommentViewHolder(private val binding: ItemCommentsBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(comment: Comment) {
            binding.userName.text = comment.userName
            binding.date.text = comment.date
            binding.commentText.text = comment.text
            binding.likeCount.text = comment.likeCount.toString()
            binding.dislikeCount.text = comment.dislikeCount.toString()
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
}