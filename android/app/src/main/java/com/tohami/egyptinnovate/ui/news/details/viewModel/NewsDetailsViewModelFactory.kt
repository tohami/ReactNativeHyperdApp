package com.tohami.egyptinnovate.ui.news.details.viewModel


import android.net.ConnectivityManager
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

import com.tohami.egyptinnovate.ui.news.details.data.NewsDetailsRepository

import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class NewsDetailsViewModelFactory @Inject
constructor(private var repository: NewsDetailsRepository, private var connectivityManager: ConnectivityManager) : ViewModelProvider.Factory {


    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        try {
            return NewsDetailsViewModel(repository, connectivityManager) as T
        } catch (e: Exception) {
            throw RuntimeException(e)
        }

    }
}