package com.shopl.sdg.component.badge.box

import androidx.annotation.DrawableRes
import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color

@Immutable
data class SDGBoxBadgeIcon(
    @DrawableRes val resId: Int,
    val tint: Color? = null,
)
