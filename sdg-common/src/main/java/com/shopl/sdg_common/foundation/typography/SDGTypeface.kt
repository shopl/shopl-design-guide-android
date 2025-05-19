package com.shopl.sdg_common.foundation.typography

import androidx.compose.runtime.Immutable
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import com.shopl.sdg_resource.R

/**
 * [SDGTypography]를 위한 Typeface 정의
 */
@Immutable
internal enum class SDGTypeface(val fontFamily: FontFamily) {
    REGULAR(
        FontFamily(
            Font(R.font.pretendard_regular, weight = FontWeight.Normal),
            Font(R.font.pretendard_jp_regular, weight = FontWeight.Normal)
        )
    ),
    SEMI_BOLD(
        FontFamily(
            Font(R.font.pretendard_semibold, weight = FontWeight.SemiBold),
            Font(R.font.pretendard_jp_semibold, weight = FontWeight.SemiBold)
        )
    );
}