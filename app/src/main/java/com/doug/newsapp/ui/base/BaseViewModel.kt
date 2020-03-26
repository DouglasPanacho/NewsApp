package com.doug.newsapp.ui.base

import androidx.lifecycle.ViewModel
import com.doug.newsapp.helpers.AndroidDisposable
import javax.inject.Inject

open class BaseViewModel :ViewModel(){

    @Inject
    lateinit var androidDisposable: AndroidDisposable


    override fun onCleared() {
        super.onCleared()
        androidDisposable.dispose()
    }
}
