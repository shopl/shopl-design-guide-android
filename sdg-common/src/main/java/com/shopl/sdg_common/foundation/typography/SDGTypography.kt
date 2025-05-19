package com.shopl.sdg_common.foundation.typography

import androidx.compose.runtime.Immutable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.sp

/**
 * Foundation - Typography
 */
@Immutable
sealed class SDGTypography(val style: TextStyle) {
    data object NaviTitle : SDGTypography(
        TextStyle(
            fontSize = 19.sp,
            lineHeight = 24.sp,
            fontFamily = SDGTypeface.REGULAR.fontFamily
        )
    )

    data object Title1SB : SDGTypography(
        TextStyle(
            fontSize = 20.sp,
            lineHeight = 24.sp,
            fontFamily = SDGTypeface.SEMI_BOLD.fontFamily
        )
    )

    data object Title1R : SDGTypography(
        TextStyle(
            fontSize = 20.sp,
            lineHeight = 24.sp,
            fontFamily = SDGTypeface.REGULAR.fontFamily
        )
    )

    data object Title2SB : SDGTypography(
        TextStyle(
            fontSize = 18.sp,
            lineHeight = 22.sp,
            fontFamily = SDGTypeface.SEMI_BOLD.fontFamily
        )
    )

    data object Title2R : SDGTypography(
        TextStyle(
            fontSize = 18.sp,
            lineHeight = 22.sp,
            fontFamily = SDGTypeface.REGULAR.fontFamily
        )
    )

    data object Body1SB : SDGTypography(
        TextStyle(
            fontSize = 16.sp,
            lineHeight = 20.sp,
            fontFamily = SDGTypeface.SEMI_BOLD.fontFamily
        )
    )

    data object Body1R : SDGTypography(
        TextStyle(
            fontSize = 16.sp,
            lineHeight = 20.sp,
            fontFamily = SDGTypeface.REGULAR.fontFamily
        )
    )

    data object Body2SB : SDGTypography(
        TextStyle(
            fontSize = 14.sp,
            lineHeight = 18.sp,
            fontFamily = SDGTypeface.SEMI_BOLD.fontFamily
        )
    )

    data object Body2R : SDGTypography(
        TextStyle(
            fontSize = 14.sp,
            lineHeight = 18.sp,
            fontFamily = SDGTypeface.REGULAR.fontFamily
        )
    )

    data object Body3SB : SDGTypography(
        TextStyle(
            fontSize = 12.sp,
            lineHeight = 16.sp,
            fontFamily = SDGTypeface.SEMI_BOLD.fontFamily
        )
    )

    data object Body3R : SDGTypography(
        TextStyle(
            fontSize = 12.sp,
            lineHeight = 16.sp,
            fontFamily = SDGTypeface.REGULAR.fontFamily
        )
    )
}
