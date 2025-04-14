package com.mekanly.utils

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.mekanly.utils.extensions.dpToPx

class SpacingItemDecoration(
    private val spacingInDp: Float,
    private val orientation: Int = RecyclerView.VERTICAL,
    private val includeEdge: Boolean = true
) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect, view: View,
        parent: RecyclerView, state: RecyclerView.State
    ) {
        val position = parent.getChildAdapterPosition(view)
        val spacing = spacingInDp.dpToPx()

        if (orientation == RecyclerView.VERTICAL) {
            outRect.left = if (includeEdge) spacing else 0
            outRect.right = if (includeEdge) spacing else 0
            outRect.top = if (includeEdge && position == 0) spacing else 0
            outRect.bottom = spacing
        } else {
            outRect.top = if (includeEdge) spacing else 0
            outRect.bottom = if (includeEdge) spacing else 0
            outRect.left = if (includeEdge && position == 0) spacing else 0
            outRect.right = spacing
        }
    }
}