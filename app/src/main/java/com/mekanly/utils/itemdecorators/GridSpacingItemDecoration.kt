package com.mekanly.utils.itemdecorators

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.mekanly.utils.extensions.dpToPx

class GridSpacingItemDecoration(
    private val spanCount: Int,
    private val spacingInDp: Float,
    private val includeEdge: Boolean
) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect, view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        val position = parent.getChildAdapterPosition(view) // item position
        val column = position % spanCount // item column
        val spacing = spacingInDp.dpToPx()
        if (includeEdge) {
            outRect.left = spacing - column * spacing / spanCount
            outRect.right = (column + 1) * spacing / spanCount

            if (position < spanCount) {
                outRect.top = spacing
            }
            outRect.bottom = spacing
        } else {
            outRect.left = column * spacing / spanCount
            outRect.right = spacing - (column + 1) * spacing / spanCount
            if (position >= spanCount) {
                outRect.top = spacing
            }
        }
    }
}