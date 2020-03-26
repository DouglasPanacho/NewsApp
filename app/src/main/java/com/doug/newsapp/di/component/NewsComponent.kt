package com.doug.newsapp.di.component

import com.doug.newsapp.di.module.home.NewsModule
import com.doug.newsapp.ui.news.NewsFragment
import dagger.Subcomponent

@Subcomponent(modules = [NewsModule::class])
interface NewsComponent {
    @Subcomponent.Factory
    interface Factory {
        fun create(): NewsComponent
    }

    fun inject(fragment: NewsFragment)
}
