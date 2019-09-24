package com.tohami.egyptinnovate.ui.news.details.viewModel

import android.net.ConnectivityManager

import com.tohami.egyptinnovate.data.model.DataModel
import com.tohami.egyptinnovate.data.model.NewsDetails
import com.tohami.egyptinnovate.ui.base.BaseViewModel
import com.tohami.egyptinnovate.ui.news.details.data.NewsDetailsRepository
import com.tohami.egyptinnovate.utilities.Constants

import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.BehaviorSubject

class NewsDetailsViewModel internal constructor(private val newsDetailsRepository: NewsDetailsRepository, connectivityManager: ConnectivityManager) : BaseViewModel(connectivityManager) {
    private val newsSubject = BehaviorSubject.create<DataModel<NewsDetails>>()

    fun getNewsDetails(id: String): Observable<DataModel<NewsDetails>> {

        if (!isNetworkAvailable) {
            val error = DataModel<NewsDetails>(Constants.Status.NO_NETWORK, null, null)
            newsSubject.onNext(error)
            return newsSubject
        }

        addDisposable(newsDetailsRepository.getNewsDetails(id).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe({ newsDetailsResponse ->
                    val newsDetailsDataModel = DataModel<NewsDetails>(Constants.Status.SUCCESS, null, newsDetailsResponse.newsDetails)
                    newsSubject.onNext(newsDetailsDataModel)
                }, { throwable ->
                    val error = DataModel<NewsDetails>(Constants.Status.FAIL, throwable.message, null)
                    newsSubject.onNext(error)
                })
        )

        //send loading
        val loading = DataModel<NewsDetails>(Constants.Status.LOADING, null, null)
        newsSubject.onNext(loading)

        return newsSubject
    }
}
