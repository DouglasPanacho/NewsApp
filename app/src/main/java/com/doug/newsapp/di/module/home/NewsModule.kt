package com.doug.newsapp.di.module.home


import androidx.lifecycle.ViewModel
import com.doug.newsapp.di.module.base.ViewModelFactoryModule
import com.doug.newsapp.ui.news.NewsViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

/**
 * Factory used to provide injection of different ViewModels from the default one(no parameters)
 */
@Module(includes =[ NewsModule.BindsViewModel::class])
class NewsModule {

    @Module
    interface BindsViewModel {
        @Binds
        @IntoMap
        @ViewModelFactoryModule.ViewModelKey(NewsViewModel::class)
        fun bindMyViewModel(myViewModel: NewsViewModel): ViewModel
    }



}
