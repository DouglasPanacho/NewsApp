package com.doug.newsapp.di.module.base

import com.doug.newsapp.data.remote.ServiceGenerator
import com.doug.newsapp.data.remote.services.NewsService
import com.doug.newsapp.data.repositories.NewsRepository
import com.doug.newsapp.data.repositories.NewsRepositoryImp
import com.douglaspanacho.news.utils.Constants.NetworkConstants
import dagger.Binds
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule {

    @Singleton
    @Provides
    fun providesRepository(newsService: NewsService): NewsRepository =
        NewsRepositoryImp(newsService)


    @Singleton
    @Provides
    fun provideNewsService(): NewsService =
        ServiceGenerator.createService(
            NewsService::class.java,
            NetworkConstants.BASE_URL
        )

}
