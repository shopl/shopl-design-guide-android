package com.shopl.sdg.component.badge.box.preview

import androidx.compose.ui.graphics.Color
import com.shopl.sdg.component.badge.box.SDGBoxBadgeSize
import com.shopl.sdg.component.badge.box.SDGBoxBadgeType

internal data class SDGBoxBadgePreviewParam(
    val size: SDGBoxBadgeSize,
    val type: SDGBoxBadgeType,
    val label: String,
    val labelColor: Color,
    val backgroundColor: Color,
    val isFillMaxWidth: Boolean = false,
    val enable: Boolean = true,
    val leftIcon: Int? = null,
    val leftIconTint: Color? = null,
    val rightIcon: Int? = null,
    val rightIconTint: Color? = null,
)
