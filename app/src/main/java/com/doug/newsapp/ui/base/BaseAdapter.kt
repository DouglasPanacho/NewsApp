package com.doug.newsapp.ui.base

import android.view.View
import androidx.recyclerview.widget.RecyclerView

/**
 * Base adapter used to avoid boilerplate
 */

abstract class BaseAdapter<T : Any> : RecyclerView.Adapter<BaseAdapter.VH<T>>() {


    companion object {
       const val HEADER_VIEW_TYPE = 1
       const val NEWS_VIEW_TYPE = 2
    }

    interface OnItemClickListener {
        fun onItemClicked(t: Any)
    }

    abstract class VH<T>(view: View) : RecyclerView.ViewHolder(view) {
        abstract fun bind(item: T)
    }

    private var items: MutableList<T> = mutableListOf()

    protected lateinit var listener: OnItemClickListener

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: VH<T>, position: Int) {
        holder.bind(items[position])
    }

    /**
     * Adds new items to the list and clears it if necessary
     * @param newItems List of items want to show
     * @param clear To clear the list or not
     */
    fun addNewsItems(newItems: MutableList<T>, clear: Boolean) {
        if (clear) {
            items.clear()
            items.addAll(newItems)
            notifyDataSetChanged()
        } else {
            items.addAll(newItems)
            notifyItemRangeChanged(items.size - newItems.size, items.size)
        }

    }

    fun getListItems(): MutableList<T> {
        return items
    }

    abstract fun setOnClickListener(listener: OnItemClickListener)

}
