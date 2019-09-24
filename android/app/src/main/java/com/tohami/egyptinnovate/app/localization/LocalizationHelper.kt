package com.tohami.egyptinnovate.app.localization

import android.content.Context
import java.util.*

/**
 * Created by Sherif.ElNady on 10/19/2016.
 */
object LocalizationHelper {

    fun changeAppLanguage(languageToLoad: String?, ctx: Context) {

        try {
            if (!languageToLoad.isNullOrEmpty()) {
                val res = ctx.applicationContext.resources
                val config = res.configuration

                val locale = Locale(languageToLoad)
                Locale.setDefault(locale)
                config.setLocale(locale)
                ctx.resources.updateConfiguration(config, ctx.resources.displayMetrics)

            }

        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

}
