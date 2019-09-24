package com.tohami.egyptinnovate.ui.news.list.viewModel

import android.net.ConnectivityManager

import com.tohami.egyptinnovate.data.model.DataModel
import com.tohami.egyptinnovate.data.model.NewsItem
import com.tohami.egyptinnovate.ui.base.BaseViewModel
import com.tohami.egyptinnovate.ui.news.list.data.NewsRepository
import com.tohami.egyptinnovate.utilities.Constants

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.BehaviorSubject

class NewsListViewModel(private val newsRepository: NewsRepository, connectivityManager: ConnectivityManager) : BaseViewModel(connectivityManager) {

    val newsSubject = BehaviorSubject.create<DataModel<List<NewsItem>>>()

    fun refreshNewsList() {

        if (!isNetworkAvailable) {
            val error = DataModel<List<NewsItem>>(Constants.Status.NO_NETWORK, null, null)
            newsSubject.onNext(error)
            return
        }

        addDisposable(
                newsRepository.news
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                { getNewsResponse ->
                                    val newsListDataModel = DataModel(Constants.Status.SUCCESS, null, getNewsResponse.newsItems)
                                    newsSubject.onNext(newsListDataModel)
                                }, { throwable ->
                            val error = DataModel<List<NewsItem>>(Constants.Status.FAIL, throwable.message, null)
                            newsSubject.onNext(error)
                        })
        )

        val loading = DataModel<List<NewsItem>>(Constants.Status.LOADING, null, null)
        newsSubject.onNext(loading)
    }


}
