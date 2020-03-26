package com.doug.newsapp.di.module.base

import com.doug.newsapp.data.remote.ServiceGenerator
import com.doug.newsapp.data.remote.services.NewsDataManager
import com.doug.newsapp.data.remote.services.NewsService
import com.doug.newsapp.data.repositories.NewsRepository
import com.doug.newsapp.data.repositories.NewsRepositoryImp
import com.douglaspanacho.news.utils.Constants.NetworkConstants
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule {

    @Singleton
    @Provides
    fun providesRepository(newsDataManager: NewsDataManager): NewsRepository =
        NewsRepositoryImp(newsDataManager)


    @Singleton
    @Provides
    fun provideNewsDataManager(): NewsDataManager =
        NewsDataManager(
            ServiceGenerator.createService(
                NewsService::class.java,
                NetworkConstants.BASE_URL
            )
        )


}
