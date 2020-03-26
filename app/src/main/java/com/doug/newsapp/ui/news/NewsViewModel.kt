package com.doug.newsapp.ui.news

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.doug.newsapp.data.remote.models.Article
import com.doug.newsapp.data.repositories.NewsRepository
import com.doug.newsapp.helpers.constants.Constants
import com.doug.newsapp.ui.base.BaseViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class NewsViewModel @Inject constructor() : BaseViewModel() {


    @Inject
    lateinit var newsRepository: NewsRepository

    private var _news: MutableLiveData<MutableList<Article>> = MutableLiveData()
    private var news: LiveData<MutableList<Article>> = _news

    private var _status: MutableLiveData<Constants.VIEW_STATUS> = MutableLiveData()
    private var status: LiveData<Constants.VIEW_STATUS> = _status

    fun getNewsLiveData() = news

    // Used to control the view status, like showing loading view, error, etc
    fun getStatus() = status


    /**
     * Method responsible to get all the headline news and update the
     * view status
     *  @param page The current page to request  from api
     * @param country The desired country **/
    fun getNews(page: Int, country: String = "us") {
        _status.postValue(Constants.VIEW_STATUS.LOADING)
        androidDisposable += newsRepository.getHeadlineNews(page, country)
            .subscribeOn(Schedulers.io())
            .doOnNext(Consumer {
                if (it.size == 0 && page == 0) {
                    _status.postValue(Constants.VIEW_STATUS.EMPTY)
                } else {
                    _status.postValue(Constants.VIEW_STATUS.SUCCESS)
                    _news.postValue(it)
                }
            })
            .observeOn(AndroidSchedulers.mainThread()).subscribe()
    }


}
