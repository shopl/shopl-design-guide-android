package com.shopl.sdg.component.badge.box.preview

import androidx.compose.ui.graphics.Color
import com.shopl.sdg.component.badge.box.SDGBoxBadgeFontWeight
import com.shopl.sdg.component.badge.box.SDGBoxBadgeIcon
import com.shopl.sdg.component.badge.box.SDGBoxBadgeStyle

internal data class SDGBoxBadgePreviewParam(
    val style: SDGBoxBadgeStyle,
    val fontWeight: SDGBoxBadgeFontWeight,
    val label: String,
    val labelColor: Color,
    val backgroundColor: Color,
    val isFillMaxWidth: Boolean = false,
    val leftIc: SDGBoxBadgeIcon? = null,
    val rightIc: SDGBoxBadgeIcon? = null,
)
