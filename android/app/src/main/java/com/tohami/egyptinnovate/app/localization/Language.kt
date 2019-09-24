package com.tohami.egyptinnovate.app.localization

import com.tohami.egyptinnovate.utilities.Constants

enum class Language (val code: String) {
    ENGLLSH(Constants.LocaleCode.ENGLISH),
    ARABIC(Constants.LocaleCode.ARABIC),
    DEFAULT(Constants.LocaleCode.DEFAULT_LANGUAGE);

    companion object {

        fun fromLocaleCode(value: String?): Language {
            if (value == null) {
                return DEFAULT
            }
            return when (value) {
                Constants.LocaleCode.ARABIC -> ARABIC

                Constants.LocaleCode.ENGLISH -> ENGLLSH

                else -> DEFAULT
            }

        }
    }
}
