package com.doug.newsapp

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.doug.newsapp.data.remote.models.Article
import com.doug.newsapp.data.remote.models.NewsModel
import com.doug.newsapp.data.remote.models.Source
import com.doug.newsapp.data.remote.services.NewsDataManager
import com.doug.newsapp.data.remote.services.NewsService
import com.doug.newsapp.data.repositories.NewsRepository
import com.doug.newsapp.data.repositories.NewsRepositoryImp
import io.reactivex.Observable
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.plugins.RxJavaPlugins
import io.reactivex.schedulers.Schedulers
import org.junit.Rule
import org.mockito.ArgumentMatchers
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

abstract class BaseTestClass {

    lateinit var newsRepository: NewsRepository

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Mock
    lateinit var service: NewsService


    private var article: Article

    lateinit var newsDataManager: NewsDataManager

    init {
        RxJavaPlugins.setIoSchedulerHandler { Schedulers.trampoline() }
        RxJavaPlugins.setComputationSchedulerHandler { Schedulers.trampoline() }
        RxJavaPlugins.setNewThreadSchedulerHandler { Schedulers.trampoline() }
        RxAndroidPlugins.setInitMainThreadSchedulerHandler { Schedulers.trampoline() }
        article = Article(
            Source("Test"),
            "Test",
            "Test",
            "",
            "",
            ""
        )
    }

    fun setup() {
        MockitoAnnotations.initMocks(this)
        newsDataManager =
            NewsDataManager(service)
        newsRepository =
            NewsRepositoryImp(newsDataManager)
        Mockito.`when`(
            newsDataManager.getHeadlineNews(
                ArgumentMatchers.anyInt(),
                ArgumentMatchers.anyString()
            )
        ).thenReturn(
            Observable.just(
                NewsModel(
                    mutableListOf(article, article),
                    "Success",
                    12
                )
            )
        )
    }
}
