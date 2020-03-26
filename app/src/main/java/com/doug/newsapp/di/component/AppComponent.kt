package com.doug.newsapp.di.component

import com.doug.newsapp.di.module.SubComponentsModule
import com.doug.newsapp.di.module.base.AppModule

import dagger.Component
import javax.inject.Singleton


@Singleton
@Component(modules = [AppModule::class, SubComponentsModule::class])
interface AppComponent {
    @Component.Factory
    interface Factory {
        fun create(): AppComponent
    }

    fun provideNewsComponent(): NewsComponent.Factory

}

