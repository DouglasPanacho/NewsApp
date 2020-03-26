package com.doug.newsapp.helpers

import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import javax.inject.Inject

class AndroidDisposable @Inject constructor(){
    private var compositeDisposable: CompositeDisposable? = null

    /**
     * Adds new disposable to list
     * @param disposable
     */
    fun add(disposable: Disposable) {
        if (compositeDisposable == null) {
            compositeDisposable = CompositeDisposable()
        }
        compositeDisposable?.add(disposable)
    }

    /**
     * Dispose all the items from the list
     */
    fun dispose() {
        compositeDisposable?.dispose()
        compositeDisposable = null
    }

    operator fun plusAssign(disposable: Disposable) {
        add(disposable)
    }


}
