package com.shopl.sdg_common.foundation.typography

import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.sp

/**
 * Foundation - Typography
 */
sealed class IOTypography(val style: TextStyle) {
    data object NaviTitle : IOTypography(
        TextStyle(
            fontSize = 19.sp,
            lineHeight = 24.sp,
            fontFamily = IOTypeface.REGULAR.fontFamily
        )
    )

    data object Title1SB : IOTypography(
        TextStyle(
            fontSize = 20.sp,
            lineHeight = 24.sp,
            fontFamily = IOTypeface.SEMI_BOLD.fontFamily
        )
    )

    data object Title1R : IOTypography(
        TextStyle(
            fontSize = 20.sp,
            lineHeight = 24.sp,
            fontFamily = IOTypeface.REGULAR.fontFamily
        )
    )

    data object Title2SB : IOTypography(
        TextStyle(
            fontSize = 18.sp,
            lineHeight = 22.sp,
            fontFamily = IOTypeface.SEMI_BOLD.fontFamily
        )
    )

    data object Title2R : IOTypography(
        TextStyle(
            fontSize = 18.sp,
            lineHeight = 22.sp,
            fontFamily = IOTypeface.REGULAR.fontFamily
        )
    )

    data object Body1SB : IOTypography(
        TextStyle(
            fontSize = 16.sp,
            lineHeight = 20.sp,
            fontFamily = IOTypeface.SEMI_BOLD.fontFamily
        )
    )

    data object Body1R : IOTypography(
        TextStyle(
            fontSize = 16.sp,
            lineHeight = 20.sp,
            fontFamily = IOTypeface.REGULAR.fontFamily
        )
    )

    data object Body2SB : IOTypography(
        TextStyle(
            fontSize = 14.sp,
            lineHeight = 18.sp,
            fontFamily = IOTypeface.SEMI_BOLD.fontFamily
        )
    )

    data object Body2R : IOTypography(
        TextStyle(
            fontSize = 14.sp,
            lineHeight = 18.sp,
            fontFamily = IOTypeface.REGULAR.fontFamily
        )
    )

    data object Body3SB : IOTypography(
        TextStyle(
            fontSize = 12.sp,
            lineHeight = 16.sp,
            fontFamily = IOTypeface.SEMI_BOLD.fontFamily
        )
    )

    data object Body3R : IOTypography(
        TextStyle(
            fontSize = 12.sp,
            lineHeight = 16.sp,
            fontFamily = IOTypeface.REGULAR.fontFamily
        )
    )
}
