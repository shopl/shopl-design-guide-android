package com.shopl.sdg.component.tab.box

import androidx.compose.ui.graphics.Color
import com.shopl.sdg_common.foundation.SDGColor
import com.shopl.sdg_common.foundation.typography.SDGTypography

sealed interface SDGBoxTabItemState {
    val labelTypography: SDGTypography
    val labelTextColor: Color
    val twoDepthTextColor: Color

    data object Selected : SDGBoxTabItemState {
        override val labelTypography = SDGTypography.Body2SB
        override val labelTextColor = SDGColor.Neutral700
        override val twoDepthTextColor = SDGColor.Neutral500
    }

    data object Unselected : SDGBoxTabItemState {
        override val labelTypography = SDGTypography.Body2R
        override val labelTextColor = SDGColor.Neutral350
        override val twoDepthTextColor = SDGColor.Neutral350
    }
}
