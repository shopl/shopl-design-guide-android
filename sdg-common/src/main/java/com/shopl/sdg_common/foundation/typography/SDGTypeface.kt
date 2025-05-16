package com.shopl.sdg_common.foundation.typography

import android.graphics.Typeface
import androidx.compose.ui.text.font.FontFamily

internal enum class IOTypeface {
    REGULAR, SEMI_BOLD;

    val fontFamily: FontFamily by lazy {
        FontFamily(
            typeface = when (this) {
                REGULAR -> Typeface.create(Typeface.DEFAULT, Typeface.NORMAL)
                SEMI_BOLD -> Typeface.create(Typeface.DEFAULT, Typeface.BOLD)
            }
        )
    }
}