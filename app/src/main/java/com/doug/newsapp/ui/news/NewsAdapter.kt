package com.doug.newsapp.ui.news

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.doug.newsapp.R
import com.doug.newsapp.data.remote.models.Article
import com.doug.newsapp.ui.base.BaseAdapter
import kotlinx.android.synthetic.main.news_item_layout.view.*
import javax.inject.Inject

class NewsAdapter @Inject constructor() : BaseAdapter<Article>() {

    private lateinit var requestManager: RequestManager

    override fun getItemViewType(position: Int): Int = if (position % 7 == 0) HEADER_VIEW_TYPE
    else NEWS_VIEW_TYPE

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH<Article> {
        requestManager = Glide.with(parent.context)
        var viewHolder: VH<Article> = if (viewType == HEADER_VIEW_TYPE) {
            NewsViewHolder(
                requestManager,
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.header_news_item_layout, parent, false)
            )

        } else {
            NewsViewHolder(
                requestManager,
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.news_item_layout, parent, false)
            )
        }

        viewHolder.itemView.setOnClickListener {
            listener.onItemClicked(
                getListItems()[viewHolder.adapterPosition]
            )
        }
        return viewHolder
    }

    override fun setOnClickListener(clickListener: OnItemClickListener) {
        listener = clickListener
    }

    class NewsViewHolder(private val requestManager: RequestManager, itemview: View) :
        VH<Article>(itemview) {

        private val titleTv: TextView = itemview.titleTextView
        private val subtitleTv: TextView = itemview.descriptionTextView
        private val fontTv: TextView = itemview.fontTextView
        private val timeTv: TextView = itemview.timeTextView
        private val newsImage: ImageView = itemview.newsImage

        override fun bind(item: Article) {
            requestManager.load(item.urlToImage)
                .centerCrop()
                .error(R.drawable.ic_news_placeholder)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(newsImage)
            titleTv.text = item.title
            subtitleTv.text = item.description
            fontTv.text = itemView.context.getString(R.string.from_label, item.source?.name)
            timeTv.text = itemView.context.getString(R.string.hours_ago_label, item.publishedAt)


        }
    }

}
