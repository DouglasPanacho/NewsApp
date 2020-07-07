package com.doug.newsapp.data.repositories


import com.doug.newsapp.data.remote.models.Article
import io.reactivex.Observable
import io.reactivex.Single

interface NewsRepository {
    /** Function used to get the headline news from a specific country
     * @param page The current page to request  from api
     * @param country The desired country**/
    fun getHeadlineNews(page: Int, country: String): Single<MutableList<Article>>
}
