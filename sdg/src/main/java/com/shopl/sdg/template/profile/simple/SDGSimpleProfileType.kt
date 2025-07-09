package com.shopl.sdg.template.profile.simple

import androidx.compose.ui.graphics.Color
import com.shopl.sdg_common.foundation.SDGColor
import com.shopl.sdg_common.foundation.typography.SDGTypography

/**
 * typography, color 등을 결정하는 타입
 */
enum class SDGSimpleProfileType(val textColor: Color, val typography: SDGTypography) {
    NORMAL(textColor = SDGColor.Neutral700, typography = SDGTypography.Body2R),
    EMPHA(textColor = SDGColor.Neutral700, typography = SDGTypography.Body2SB),
    SUB(textColor = SDGColor.Neutral400, typography = SDGTypography.Body2R),
}