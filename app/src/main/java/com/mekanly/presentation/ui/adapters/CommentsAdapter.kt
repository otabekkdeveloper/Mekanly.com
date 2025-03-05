package com.mekanly.presentation.ui.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.mekanly.R
import com.mekanly.data.DataComments


class CommentsAdapter(private val comments: List<DataComments>) : RecyclerView.Adapter<CommentsAdapter.CommentViewHolder>() {

    inner class CommentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val userName: TextView = itemView.findViewById(R.id.user_name)
        private val date: TextView = itemView.findViewById(R.id.date)
        private val commentText: TextView = itemView.findViewById(R.id.comment_text)
        private val likeIcon: ImageView = itemView.findViewById(R.id.like_icon)
        private val likeCount: TextView = itemView.findViewById(R.id.like_count)
        private val dislikeIcon: ImageView = itemView.findViewById(R.id.dislike_icon)
        private val dislikeCount: TextView = itemView.findViewById(R.id.dislike_count)

        @SuppressLint("SetTextI18n")
        fun bind(comment: DataComments) {
            userName.text = comment.userName
            date.text = comment.date
            commentText.text = comment.text
            likeCount.text = comment.likeCount.toString()
            dislikeCount.text = comment.dislikeCount.toString()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_comments, parent, false)
        return CommentViewHolder(view)
    }

    override fun onBindViewHolder(holder: CommentViewHolder, position: Int) {
        holder.bind(comments[position])
    }

    override fun getItemCount(): Int = comments.size
}
