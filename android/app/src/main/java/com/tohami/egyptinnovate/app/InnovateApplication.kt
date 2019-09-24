package com.tohami.egyptinnovate.app

import com.facebook.react.ReactApplication
import com.facebook.react.ReactNativeHost
import com.facebook.soloader.SoLoader
import com.squareup.picasso.OkHttp3Downloader
import com.squareup.picasso.Picasso
import com.tohami.egyptinnovate.app.di.DaggerApplicationComponent
import dagger.android.AndroidInjector
import dagger.android.support.DaggerApplication
import com.facebook.react.ReactPackage
import com.tohami.egyptinnovate.BuildConfig
import com.facebook.react.shell.MainReactPackage
import java.util.*
import java.util.Arrays.asList




class InnovateApplication : DaggerApplication(), ReactApplication {

    private val mReactNativeHost = object : ReactNativeHost(this) {
        override fun getUseDeveloperSupport(): Boolean {
            return BuildConfig.DEBUG
        }

        override fun getPackages(): List<ReactPackage> {
// Packages that cannot be autolinked yet can be added manually here, for example:
            // packages.add(new MyReactNativePackage());
            return Arrays.asList(
                    MainReactPackage()
            );
        }

        override fun getJSMainModuleName(): String {
            return "index"
        }
    }

    override fun getReactNativeHost(): ReactNativeHost {
        return mReactNativeHost
    }


    override fun onCreate() {
        super.onCreate()
        setupPicasso()
        SoLoader.init(this, /* native exopackage */ false);

    }

    private fun setupPicasso() {
        Picasso.Builder(this).apply {
            downloader(OkHttp3Downloader(this@InnovateApplication, Integer.MAX_VALUE.toLong()))
            loggingEnabled(true)
            indicatorsEnabled(true)
        }.run {
            try {
                Picasso.setSingletonInstance(build())
            } catch (ex: IllegalStateException) {
                ex.printStackTrace()
            }
        }
    }

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        val component = DaggerApplicationComponent.builder().application(this).build()
        component.inject(this)

        return component
    }
}
