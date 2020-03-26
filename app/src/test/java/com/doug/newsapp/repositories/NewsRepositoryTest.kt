package com.doug.newsapp.repositories

import com.doug.newsapp.BaseTestClass
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentMatchers

class NewsRepositoryTest : BaseTestClass() {

    @Before
    fun init() {
        setup()
    }

    @Test
    fun `test get news method complete with success`() {
            newsRepository.getHeadlineNews(ArgumentMatchers.anyInt(), ArgumentMatchers.anyString())
                .test()
                .assertComplete()
                .assertNoErrors()
    }


}

