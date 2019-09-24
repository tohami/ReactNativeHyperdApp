package com.tohami.egyptinnovate.dagger.module

import com.tohami.egyptinnovate.ui.main.MainActivity

import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBindingModule {

    @ContributesAndroidInjector(modules = [MainActivityBindingModule::class])
    internal abstract fun bindMainActivity(): MainActivity
}