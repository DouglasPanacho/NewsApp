package com.doug.newsapp.data.repositories

import com.doug.newsapp.data.remote.models.Article
import com.doug.newsapp.data.remote.services.NewsService
import com.doug.newsapp.helpers.toDate
import io.reactivex.Observable
import io.reactivex.Single
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NewsRepositoryImp @Inject constructor(private var service: NewsService) :
    NewsRepository {

    override fun getHeadlineNews(page: Int, country: String): Observable<MutableList<Article>> {
        return service.getHeadlineNews(page, country).map { it.articles }

    }

}
