package com.doug.newsapp.data.remote.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

data class NewsModel(
    val articles: MutableList<Article>,
    val status: String,
    val totalResults: Int
)

@Parcelize
data class Article(
    val source: Source?,
    val title: String?,
    val description: String?,
    var publishedAt: String?,
    val url: String?,
    val urlToImage: String?
) : Parcelable

@Parcelize
data class Source(val name: String?) : Parcelable
