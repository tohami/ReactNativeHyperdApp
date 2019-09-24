package com.tohami.egyptinnovate.app.di

import android.content.Context
import android.net.ConnectivityManager
import android.preference.PreferenceManager
import com.tohami.egyptinnovate.BuildConfig
import com.tohami.egyptinnovate.app.localization.LanguageSelectionInspector
import com.tohami.egyptinnovate.app.settings.AppSettings
import com.tohami.egyptinnovate.app.settings.SharedPreferenceAppSettings
import com.tohami.egyptinnovate.data.retrofit.ApiInterface
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
class ApplicationModule {

    @Provides
    @Singleton
    internal fun provideAppSettings(context: Context): AppSettings {
        return SharedPreferenceAppSettings(PreferenceManager.getDefaultSharedPreferences(context))
    }


    @Singleton
    @Provides
    internal fun provideOkHttpClient(appSettings: AppSettings): OkHttpClient {
        val interceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

        val languageSelectionInspector = LanguageSelectionInspector().apply {
            setAppSettings(appSettings)
        }

        return OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(5, TimeUnit.MINUTES)
                .addInterceptor(languageSelectionInspector)
                .addInterceptor(interceptor)
                .retryOnConnectionFailure(true)
                .build()
    }

    @Singleton
    @Provides
    internal fun provideRetrofit(client: OkHttpClient): Retrofit {
        return Retrofit.Builder().baseUrl(BuildConfig.BASE_EN_URL)
                .client(client)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
    }

    @Singleton
    @Provides
    internal fun provideRetrofitService(retrofit: Retrofit): ApiInterface {
        return retrofit.create(ApiInterface::class.java)
    }

    @Singleton
    @Provides
    internal fun provideConnectivityManager(context: Context): ConnectivityManager {
        return context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    }

}