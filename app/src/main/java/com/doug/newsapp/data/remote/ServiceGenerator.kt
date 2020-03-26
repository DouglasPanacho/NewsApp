package com.doug.newsapp.data.remote

import com.douglaspanacho.news.utils.Constants.NetworkConstants
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory


object ServiceGenerator {

    /**
     *Creates the retrofit service and set all the necessary information
     * In this case the sources and api key are set here to avoid passing them
     * every time as a parameter
     * @param serviceClass The service interface that contains all endpoints
     * @param url The base url for the service
     */
        //Used to create the services
        fun <S> createService(serviceClass: Class<S>, url: String): S {
            val retrofit = Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())

            //set the necessary querys in the request, like the api key
            val httpClient = OkHttpClient.Builder()
            httpClient.addInterceptor {
                    chain ->
                val original = chain.request()
                val originalHttpUrl = original.url()
                val url = originalHttpUrl.newBuilder()
                    .addQueryParameter(NetworkConstants.API_KEY, NetworkConstants.API_KEY_VALUE)
                    .addQueryParameter(NetworkConstants.PAGE_SIZE, NetworkConstants.PAGE_SIZE_VALUE)
                    .build()
                val requestBuilder = original.newBuilder().url(url)
                val request = requestBuilder.build()
                chain.proceed(request)
            }
            retrofit.client(httpClient.build())
            return retrofit.build().create(serviceClass)
        }
}
