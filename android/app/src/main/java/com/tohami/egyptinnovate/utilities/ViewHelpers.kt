package com.tohami.egyptinnovate.utilities

import android.content.Context
import android.widget.Toast

object ViewHelpers {

    @JvmOverloads
    fun showToast(context: Context, content: String, isLong: Boolean = false) {
        Toast.makeText(
                context,
                content,
                if (isLong) Toast.LENGTH_LONG else Toast.LENGTH_SHORT)
                .show()
    }
}
