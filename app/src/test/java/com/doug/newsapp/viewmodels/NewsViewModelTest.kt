package com.doug.newsapp.viewmodels

import com.doug.newsapp.BaseTestClass
import com.doug.newsapp.data.remote.models.Article
import com.doug.newsapp.data.remote.models.NewsModel
import com.doug.newsapp.helpers.AndroidDisposable
import com.doug.newsapp.helpers.constants.Constants
import com.doug.newsapp.ui.news.NewsViewModel
import io.reactivex.Observable
import io.reactivex.Observer
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentMatchers
import org.mockito.Mock
import org.mockito.Mockito


class NewsViewModelTest : BaseTestClass() {


    private lateinit var newsViewModel: NewsViewModel

    @Mock
    lateinit var observer: Observer<MutableList<Article>>

    @Before
    fun init() {
        setup()
        newsViewModel = NewsViewModel()
        newsViewModel.androidDisposable =
            AndroidDisposable()
        newsViewModel.newsRepository = newsRepository

    }


    @Test
    fun `test get news is called and status is success`() {
        newsViewModel.getStatus().observeForever { observer }
        newsViewModel.getNews(0)
        Assert.assertTrue(newsViewModel.getStatus().value == Constants.VIEW_STATUS.SUCCESS)
        Assert.assertTrue(newsViewModel.getNewsLiveData().value?.size == 2)
    }

    @Test
    fun `test get news is called and status is empty`() {
        Mockito.`when`(
            newsDataManager.getHeadlineNews(
                ArgumentMatchers.anyInt(),
                ArgumentMatchers.anyString()
            )
        ).thenReturn(
            Observable.just(
                NewsModel(
                    mutableListOf(),
                    "Success",
                    0
                )
            )
        )
        newsViewModel.getNews(0)
        Assert.assertTrue(newsViewModel.getStatus().value == Constants.VIEW_STATUS.EMPTY)
        Assert.assertTrue(newsViewModel.getNewsLiveData().value == null)

    }

}

