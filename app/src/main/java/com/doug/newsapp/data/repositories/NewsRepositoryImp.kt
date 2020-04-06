package com.doug.newsapp.data.repositories

import com.doug.newsapp.data.remote.models.Article
import com.doug.newsapp.data.remote.services.NewsDataManager
import com.doug.newsapp.helpers.extensions.toDate
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NewsRepositoryImp @Inject constructor(private var newsDataManager: NewsDataManager) :
    NewsRepository {

    override fun getHeadlineNews(page: Int, country: String): Observable<MutableList<Article>> {
        return newsDataManager.getHeadlineNews(page, country)
            .map { it.articles }
            .flatMapIterable { it }
            .subscribeOn(Schedulers.computation())
            .doOnNext {
                if (!it.publishedAt.isNullOrEmpty()) it.publishedAt = it.publishedAt?.toDate()
            }
            .toList()
            .onErrorReturn { mutableListOf() }
            .toObservable()

    }

}
