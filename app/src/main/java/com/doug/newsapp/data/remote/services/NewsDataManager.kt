package com.doug.newsapp.data.remote.services

import com.doug.newsapp.data.remote.models.NewsModel
import io.reactivex.Observable
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
open class NewsDataManager @Inject constructor(var service: NewsService) {

    fun getHeadlineNews(page: Int, country: String): Observable<NewsModel> = service.getHeadlineNews(page,country)

}
