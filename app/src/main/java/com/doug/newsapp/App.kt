package com.doug.newsapp

import android.app.Application
import android.content.Context
import com.doug.newsapp.di.component.AppComponent
import com.doug.newsapp.di.component.DaggerAppComponent


class App : Application() {

    lateinit var appComponent: AppComponent
    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent.create()
        context = applicationContext
    }

    companion object {
        lateinit var context: Context
        fun getApplicationContext() = context
    }
}
