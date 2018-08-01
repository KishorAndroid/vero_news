package com.kishordahiwadkar.veronews

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.OnLifecycleEvent
import android.arch.lifecycle.ViewModel
import android.util.Log
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class ArticlesFragmentViewModel : ViewModel() {

    var result: MutableLiveData<Result> = MutableLiveData()

    private var disposable: Disposable? = null

    private val newsApiService by lazy {
        NewsApiService.create()
    }

    public fun getNewsArticles(category: String?) {
        Log.d("Article", category + " " + MainActivity.apiKey)
        disposable = newsApiService.topHeadlines("in", category!!, MainActivity.apiKey!!)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    for (article in it.articles!!) {
                        Log.d("Article", article?.title)
                    }
                    result.value = it
                }, {
                    Log.d("Article", it.message)
                })
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    public fun closeConnections() {
        disposable?.dispose()
    }
}