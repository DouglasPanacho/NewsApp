package com.doug.newsapp.helpers

import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.doug.newsapp.ui.base.BaseAdapter

class PaginationScrollControl<in T : BaseAdapter<*>>(
    private val adapter: T,
    private val layoutManager: GridLayoutManager,
    private val paginationControllerListener: PaginationController
) : RecyclerView.OnScrollListener() {


    private var isLoading = false
    private var pageCount = 2
    private var lastPageCount = 1

    /**
     * Checks if the end of recyclerview was reached and then tries to get more
     * @param recyclerView The RecyclerView which scrolled.
     * @param dx The amount of horizontal scroll.
     * @param dy The amount of vertical scroll.
     */
    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        if (dy > 0) {
            if (!isLoading) {
                if (layoutManager.findLastVisibleItemPosition() == adapter.getListItems().size - 1) {
                    isLoading = true
                    if (lastPageCount != pageCount) {
                        paginationControllerListener.getMoreItems(pageCount)
                        lastPageCount = pageCount
                        pageCount++
                    }
                }
            }
        }
    }

    fun clear() {
        pageCount = 2
        lastPageCount = 1
        isLoading = false
    }

    interface PaginationController {
        fun getMoreItems(pageCount: Int=0)
    }

}
