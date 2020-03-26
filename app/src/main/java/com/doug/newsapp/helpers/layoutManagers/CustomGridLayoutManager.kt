package com.doug.newsapp.helpers.layoutManagers

import android.content.Context
import android.content.res.Configuration
import androidx.recyclerview.widget.GridLayoutManager

/**
 * A custom gridlayout responsible for setting the span count accordingly to
 * the device orientation
 * @param context The context of the view
 * @param orientation The device orientation
 */
class CustomGridLayoutManager(
    context: Context, orientation: Int
) : GridLayoutManager(context, 2, VERTICAL, false) {


    init {
        spanCount = if (orientation == Configuration.ORIENTATION_PORTRAIT) 2 else 3
        spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int = if (position % 7 == 0) {
                spanCount
            } else 1
        }
    }

}
