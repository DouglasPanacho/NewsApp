package com.doug.newsapp.helpers

import android.graphics.Rect
import androidx.recyclerview.widget.RecyclerView


/**
 * Used to set the correct space between the items accordingly to it´s span count
 * @param spanCount The current span count of the gridlayout*/
class ItemDecoratorRecyclerView(private val spanCount: Int) :
    RecyclerView.ItemDecoration() {

    override fun getItemOffsets(outRect: Rect, itemPosition: Int, parent: RecyclerView) {
        if (itemPosition % 7 != 0) {
            if (spanCount == 2) {
                if ((itemPosition % 7) % 2 == 0)
                    outRect.left = 16
            } else {
                if ((itemPosition % 7) % 3 == 1) {
                    outRect.right = 16
                } else if ((itemPosition % 7) % 3 == 0) {
                    outRect.left = 16
                }
            }
        }
    }
}
