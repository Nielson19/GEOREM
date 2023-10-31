package com.example.neil

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class ItemDecoration(private val spanCount: Int, private val spacing: Int) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        val position = parent.getChildAdapterPosition(view) // item position
        val column = position % spanCount // item column

        outRect.left = column * spacing / spanCount // spacing for the left column
        outRect.right = spacing - (column + 1) * spacing / spanCount // spacing for the right column

        if (position >= spanCount) {
            outRect.top = spacing // item has a top spacing
        }
    }
}