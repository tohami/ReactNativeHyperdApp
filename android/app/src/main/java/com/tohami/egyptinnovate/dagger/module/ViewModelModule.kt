package com.tohami.egyptinnovate.dagger.module

import androidx.lifecycle.ViewModelProvider

import com.tohami.egyptinnovate.ui.news.details.viewModel.NewsDetailsViewModelFactory
import com.tohami.egyptinnovate.ui.news.list.viewModel.NewsListViewModelFactory

import dagger.Binds
import dagger.Module

@Module
abstract class ViewModelModule {
    @Binds
    internal abstract fun bindNewsListViewModelFactory(factory: NewsListViewModelFactory): ViewModelProvider.Factory

    @Binds
    internal abstract fun bindNewsDetailsViewModelFactory(factory: NewsDetailsViewModelFactory): ViewModelProvider.Factory
}