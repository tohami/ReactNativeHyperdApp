package com.tohami.egyptinnovate.app.localization

import com.tohami.egyptinnovate.BuildConfig
import com.tohami.egyptinnovate.app.settings.AppSettings
import okhttp3.Interceptor
import java.io.IOException

class LanguageSelectionInspector : Interceptor {
    @Volatile
    private lateinit var appSettings: AppSettings

    fun setAppSettings(appSettings: AppSettings) {
        this.appSettings = appSettings
    }

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): okhttp3.Response {
        chain.request().run {
            val oldUrl = url.toString()

            val newUrl = when(appSettings.currentLanguage) {
                Language.ARABIC -> oldUrl.replace(BuildConfig.BASE_EN_URL, BuildConfig.BASE_AR_URL)
                else -> oldUrl.replace(BuildConfig.BASE_AR_URL, BuildConfig.BASE_EN_URL)
            }

            val newRequest =  newBuilder().url(newUrl).build()
            return chain.proceed(newRequest)
        }
    }
}
