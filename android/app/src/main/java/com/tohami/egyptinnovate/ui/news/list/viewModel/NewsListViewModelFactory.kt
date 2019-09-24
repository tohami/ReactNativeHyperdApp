package com.tohami.egyptinnovate.ui.news.list.viewModel


import android.net.ConnectivityManager
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

import com.tohami.egyptinnovate.ui.news.list.data.NewsRepository

import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class NewsListViewModelFactory @Inject
constructor(private val repository: NewsRepository, private val connectivityManager: ConnectivityManager) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        try {
            return NewsListViewModel(repository, connectivityManager) as T
        } catch (e: Exception) {
            throw RuntimeException(e)
        }

    }
}