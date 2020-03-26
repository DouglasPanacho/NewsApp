package com.doug.newsapp.data.remote.services


import com.doug.newsapp.data.remote.models.NewsModel
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsService {

    @GET("top-headlines")
    fun getHeadlineNews(
        @Query("page") page: Int,
        @Query("country") country: String
    ): Observable<NewsModel>


}
