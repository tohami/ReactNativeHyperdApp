package com.tohami.egyptinnovate.app.settings

import android.content.SharedPreferences
import com.tohami.egyptinnovate.app.localization.Language
import com.tohami.egyptinnovate.utilities.Constants.LocaleCode.DEFAULT_LANGUAGE
import com.tohami.egyptinnovate.utilities.Constants.Prefs.LANGUAGE_KEY

class SharedPreferenceAppSettings(private val sharedPreferences: SharedPreferences) : AppSettings {
    private var currentLanguageCache: Language ? = null


    override var currentLanguage: Language
        get() {
            val cachedValue = currentLanguageCache

            return if (cachedValue == null) {
                val storedValue = sharedPreferences.getString(LANGUAGE_KEY, DEFAULT_LANGUAGE)

                currentLanguage = Language.fromLocaleCode(storedValue)

                 return currentLanguage

            }else cachedValue
        }
        set(value) {
            currentLanguageCache = value
            sharedPreferences.edit().putString(LANGUAGE_KEY, value.code).apply()
        }
}