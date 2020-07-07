package com.doug.newsapp.data.repositories

import com.doug.newsapp.data.remote.models.Article
import com.doug.newsapp.data.remote.services.NewsDataManager
import com.doug.newsapp.helpers.toDate
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NewsRepositoryImp @Inject constructor(private var newsDataManager: NewsDataManager) :
    NewsRepository {

    override fun getHeadlineNews(page: Int, country: String): Single<MutableList<Article>> {
        return newsDataManager.getHeadlineNews(page, country)
            .map { it.articles }
            .flatMapIterable { it }
            .doOnNext {
                if (!it.publishedAt.isNullOrEmpty()) it.publishedAt = it.publishedAt?.toDate()
            }
            .toList()
    }

}
