package com.shopl.sdg.template.profile.second

import androidx.compose.ui.graphics.Color
import com.shopl.sdg_common.foundation.SDGColor
import com.shopl.sdg_common.foundation.typography.SDGTypography

enum class SDGSecondProfileType(
    val userNameTypography: SDGTypography,
    val groupNameTypography: SDGTypography,
    val userNameColor: Color,
    val groupNameColor: Color
) {
    Normal(
        userNameTypography = SDGTypography.Body2R,
        groupNameTypography = SDGTypography.Body3R,
        userNameColor = SDGColor.Neutral700,
        groupNameColor = SDGColor.Neutral500
    ),
    Empha(
        userNameTypography = SDGTypography.Body2SB,
        groupNameTypography = SDGTypography.Body3R,
        userNameColor = SDGColor.Neutral700,
        groupNameColor = SDGColor.Neutral700
    ),
}