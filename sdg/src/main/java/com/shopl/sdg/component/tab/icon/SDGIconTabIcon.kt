package com.shopl.sdg.component.tab.icon

import androidx.annotation.DrawableRes
import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color

/** Figma의 Icon Tab ic에 대응하는 Icon, Size 및 색상 정보입니다. */
@Immutable
data class SDGIconTabIcon(
    @param:DrawableRes val icon: Int,
    val size: SDGIconTabIconSize,
    val tint: Color? = null,
)
