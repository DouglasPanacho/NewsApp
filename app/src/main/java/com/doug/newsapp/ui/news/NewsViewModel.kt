package com.doug.newsapp.ui.news

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.doug.newsapp.data.remote.models.Article
import com.doug.newsapp.data.repositories.NewsRepository
import com.doug.newsapp.helpers.ViewStatus
import com.doug.newsapp.ui.base.BaseViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class NewsViewModel @Inject constructor() : BaseViewModel() {


    @Inject
    lateinit var newsRepository: NewsRepository

    private var viewStatus: MutableLiveData<ViewStatus> = MutableLiveData()
    var savedNews: MutableList<Article> = mutableListOf()

    fun getViewStatus(): LiveData<ViewStatus> = viewStatus

    /**
     * Method responsible to get all the headline news and update the
     * view status
     * @param page The current page to request  from api
     * @param country The desired country **/
    fun getNews(page: Int, country: String = "us") {
        androidDisposable += newsRepository.getHeadlineNews(page, country)
            .subscribeOn(Schedulers.io())
            .onErrorReturn {
                mutableListOf()
            }
            .doOnError {
                viewStatus.postValue(ViewStatus.ErrorStatus)
            }
            .doOnSuccess {
                if (it.size == 0 && page == 0) {
                    // viewStatus.postValue(Constants.VIEW_STATUS.EMPTY)
                    viewStatus.postValue(ViewStatus.LoadingStatus)
                } else {
                    savedNews.addAll(it)
                    viewStatus.postValue(ViewStatus.SuccessStatus(it))
                }
            }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe()
    }


}
