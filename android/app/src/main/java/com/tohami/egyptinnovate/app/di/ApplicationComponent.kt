package com.tohami.egyptinnovate.app.di

import android.app.Application

import com.tohami.egyptinnovate.app.InnovateApplication
import com.tohami.egyptinnovate.dagger.module.ActivityBindingModule
import com.tohami.egyptinnovate.dagger.module.ContextModule

import javax.inject.Singleton

import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import dagger.android.support.DaggerApplication

@Singleton
@Component(modules = [ContextModule::class, ApplicationModule::class, AndroidSupportInjectionModule::class, ActivityBindingModule::class])
interface ApplicationComponent : AndroidInjector<DaggerApplication> {

    fun inject(application: InnovateApplication)

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder

        fun build(): ApplicationComponent
    }
}