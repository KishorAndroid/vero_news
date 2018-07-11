package com.kishordahiwadkar.veronews

import io.reactivex.Observable
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApiService{

    @GET("top-headlines")
    fun topHeadlines(@Query("country") country: String, @Query("category") category: String, @Query("apiKey") apiKey: String) : Observable<Result>

    companion object {
        fun create(): NewsApiService{
            val retrofit = Retrofit.Builder()
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .baseUrl("https://newsapi.org/v2/")
                    .build()

            return retrofit.create(NewsApiService::class.java)
        }
    }
}