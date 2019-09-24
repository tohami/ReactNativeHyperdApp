package com.tohami.egyptinnovate.dagger.module

import com.tohami.egyptinnovate.ui.news.details.view.NewsDetailsFragment
import com.tohami.egyptinnovate.ui.news.list.view.NewsListFragment

import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module(includes = [ViewModelModule::class])
abstract class MainActivityBindingModule {

    @ContributesAndroidInjector
    internal abstract fun provideNewsListFragment(): NewsListFragment

    @ContributesAndroidInjector
    internal abstract fun provideNewsDetailsFragment(): NewsDetailsFragment
}
