package com.shopl.sdg.component.icon_label

import androidx.annotation.DrawableRes
import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color

/**
 * Icon Label에 표시할 아이콘 정보입니다.
 */
@Immutable
data class SDGIconLabelIcon(
    @DrawableRes val resId: Int,
    val tint: Color? = null,
)
